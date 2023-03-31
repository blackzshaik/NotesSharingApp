package com.notessharingapp.data

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.ktx.Firebase
import com.notessharingapp.data.worker.AddNotesWorker
import com.notessharingapp.data.worker.UpdateNotesWorker
import com.notessharingapp.model.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NotesDataSource @Inject constructor() : FirebaseDataSource() {

    fun addNotes(context: Context, noteStr: String, title: String, color: String) {
        val addNotesWorker = OneTimeWorkRequestBuilder<AddNotesWorker>().setInputData(
            workDataOf(Field.note to noteStr, Field.title to title, Field.color to color)
        ).build()

        WorkManager.getInstance(context).enqueue(addNotesWorker)
    }

    fun getNoteById(id: String) = callbackFlow<Notes> {
        notesCollection.document(id).get().addOnSuccessListener {
            it.toObject(Notes::class.java)?.let { note -> trySend(note) }
        }

        awaitClose { }
    }.flowOn(Dispatchers.IO)

    fun updateNote(context: Context, notesId: String, note: String, title: String) {
        val addNotesWorker = OneTimeWorkRequestBuilder<UpdateNotesWorker>().setInputData(
            workDataOf(Field.note to note, Field.title to title, Field.notesId to notesId)
        ).build()
        WorkManager.getInstance(context).enqueue(addNotesWorker)
    }

    fun changeShareOption(notesId: String, checked: Boolean) {

        notesCollection.document(notesId).update(Field.public, checked).addOnCompleteListener {
            if (it.isSuccessful) {

                userCollection.document(Firebase.auth.currentUser?.uid!!)
                    .update(Field.sharedNotesCount, FieldValue.increment(if (checked) 1 else -1))

            } else {
                println("error ${it.exception}")
            }
        }
    }

    fun deleteThisNote(notesId: String) = callbackFlow<Boolean> {
        notesCollection.document(notesId).delete().addOnSuccessListener {
            trySend(true)
        }.addOnFailureListener {
            trySend(false)
        }
        awaitClose { }
    }.flowOn(Dispatchers.IO)
}