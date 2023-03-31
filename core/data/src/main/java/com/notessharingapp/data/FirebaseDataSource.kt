package com.notessharingapp.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.notessharingapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

open class FirebaseDataSource {

    val userCollection = Firebase.firestore.collection(CollectionNames.USER)
    val notesCollection = Firebase.firestore.collection(CollectionNames.NOTES)

    fun isLoggedIn()  = Firebase.auth.currentUser != null

    val myId = Firebase.auth.currentUser?.uid!!

    fun getUserData() = callbackFlow {
        userCollection.document(Firebase.auth.currentUser?.uid!!).get().addOnSuccessListener {
            trySend(it.toObject(User::class.java)).isSuccess
        }
        awaitClose {  }
    }.flowOn(Dispatchers.IO)
}