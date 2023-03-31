package com.notessharingapp.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.notessharingapp.model.Notes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor() : FirebaseDataSource() {


    fun getMyNotes() = callbackFlow<List<Notes>> {

            notesCollection.whereEqualTo(Field.authorId, Firebase.auth.currentUser?.uid).get()
                .addOnSuccessListener {
                    trySend(it.toObjects(Notes::class.java))
                }.addOnFailureListener {
                it.printStackTrace()
                println(it)
            }

        awaitClose { }
    }.flowOn(Dispatchers.IO)

    fun getNotesFromWorld() = callbackFlow<List<Notes>> {
        notesCollection.whereEqualTo(Field.public, true).get().addOnSuccessListener {
            trySend(it.toObjects(Notes::class.java))
        }.addOnFailureListener {
            it.printStackTrace()
        }
        awaitClose { }
    }.flowOn(Dispatchers.IO)



}