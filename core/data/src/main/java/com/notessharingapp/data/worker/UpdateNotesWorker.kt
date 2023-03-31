package com.notessharingapp.data.worker

import android.content.Context
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.notessharingapp.data.CollectionNames
import com.notessharingapp.data.Field

class UpdateNotesWorker (context: Context, workerParameters: WorkerParameters) :
    ListenableWorker(context, workerParameters) {
    override fun startWork(): ListenableFuture<Result> {
        return CallbackToFutureAdapter.getFuture{ completer ->
            val note = inputData.getString(Field.note)
            val title = inputData.getString(Field.title)
            val notesId = inputData.getString(Field.notesId)

            Firebase.firestore
                .collection(CollectionNames.NOTES)
                .document(notesId!!)
                .update(mapOf(Field.note to note,Field.title to title))
                .addOnCompleteListener {
                if (it.isSuccessful){
                    completer.set(Result.success())
                }else{
                    completer.set(Result.failure())
                }
            }

        }
    }
}