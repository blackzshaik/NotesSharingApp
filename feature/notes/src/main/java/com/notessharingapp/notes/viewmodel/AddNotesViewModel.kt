package com.notessharingapp.notes.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.notessharingapp.data.NotesDataSource
import com.notessharingapp.model.Notes
import com.notessharingapp.ui.backGroundColor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddNotesViewModel @Inject constructor() : ViewModel(){

    private val notesDataSource = NotesDataSource()

    var title = MutableLiveData("")
    var note = MutableLiveData("")
    var author = MutableLiveData("")
    var createdOn = MutableLiveData("")

    lateinit var notesId:String
    var oldNote : Notes? = null

    fun saveNote(context:Context){
        if (this::notesId.isInitialized){
            oldNote?.let { oNote ->
                if (oNote.note == note.value && oNote.title == title.value){
                    println("Contents are same not updating")
                }else{
                    notesDataSource.updateNote(context,notesId,note.value!!,title.value!!)
                }
            }
        }else if (note.value != "" || title.value != ""){
            println("adding notes?")
            notesDataSource.addNotes(context,note.value!!,title.value!!,
                backGroundColor.random())
        }
    }

    fun getNoteById(id: String) = notesDataSource.getNoteById(id)
    fun changeShareOption(isChecked: Boolean) {
        oldNote.let {
            notesDataSource.changeShareOption(it?.notesId!!,isChecked)
        }
    }

    fun isMyNote(): Boolean {
        return  oldNote?.authorId == notesDataSource.myId
    }

    fun deleteThisNote() = notesDataSource.deleteThisNote(oldNote?.notesId!!)
}