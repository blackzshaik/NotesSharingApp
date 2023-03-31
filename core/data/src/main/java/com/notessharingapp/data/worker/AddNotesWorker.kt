package com.notessharingapp.data.worker

import android.content.Context
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.notessharingapp.data.CollectionNames
import com.notessharingapp.data.Field
import com.notessharingapp.model.Notes
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*
import javax.inject.Inject


class AddNotesWorker @AssistedInject constructor( context: Context,
                                                  workerParameters: WorkerParameters) :
    ListenableWorker(context, workerParameters) {



    override fun startWork(): ListenableFuture<Result> {
        return CallbackToFutureAdapter.getFuture { completer ->

            val note = inputData.getString(Field.note)
            val title = inputData.getString(Field.title)
            val color = inputData.getString(Field.color)
            val doc = Firebase.firestore.collection(CollectionNames.NOTES).document()
            val notes =
                Notes(doc.id, Firebase.auth.currentUser?.displayName ?: "", Firebase.auth.currentUser?.uid!!, title!!, note!!, color!!,false,false, Date())

//            Firebase.firestore
//                .collection(CollectionNames.USER)
//                .document(Firebase.auth.currentUser?.uid!!)
//                .update("notes",FieldValue.arrayUnion(notes.notesId)).addOnCompleteListener {
//                    if (it.isSuccessful){
//                        println("Sucess")
//                    }
//                }
            doc.set(notes).addOnCompleteListener {
                if (it.isSuccessful) {

                    completer.set(Result.success())
                } else {

                    completer.set(Result.failure())
                }
            }
        }
    }
}