package com.notessharingapp.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.notessharingapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class ProfileDataSource {

    fun getMyProfile() = callbackFlow {
        Firebase.firestore.collection(CollectionNames.USER).document(Firebase.auth.currentUser?.uid!!).get().addOnSuccessListener {
            it.toObject(User::class.java)?.let { user -> trySend(user) }
        }

        awaitClose {  }
    }.flowOn(Dispatchers.IO)
}