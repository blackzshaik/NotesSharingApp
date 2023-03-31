package com.notessharingapp.data

import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.notessharingapp.model.User

class LoginDataSource(val listner: Listener? = null) : FirebaseDataSource(){


    fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {

                listner?.onLoginSuccess()

            } else {
                println("${it.exception?.message}")
                listner?.onLoginFailed(it.exception?.message.toString())
            }
        }
    }

    fun createAccount(fullName: String, nickName: String, email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val usrProfChangeReq =
                    UserProfileChangeRequest.Builder().setDisplayName(nickName).build()
                Firebase.auth.currentUser?.updateProfile(usrProfChangeReq)
                println("create account --->")
                saveUserData(Firebase.auth.currentUser?.uid!!, email, fullName, nickName)
            } else {
                println("$it")
                listner?.onLoginFailed(it.exception?.message ?: "Unknown Reason")
            }
        }
    }

    private fun saveUserData(uid: String, email: String, fullName: String, nickName: String) {
        userCollection.run {
            document(uid).set(User(uid, fullName, nickName, email)).addOnSuccessListener {
                println("user data saved --->")
                listner?.onLoginSuccess()
            }.addOnFailureListener {
                println("$it")
                listner?.onLoginFailed(it.message ?: "")
            }
        }
    }

    interface Listener {
        fun onLoginSuccess()
        fun onLoginFailed(reason: String)
    }
}