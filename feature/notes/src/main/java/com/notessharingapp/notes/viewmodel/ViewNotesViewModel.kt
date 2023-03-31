package com.notessharingapp.notes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.notessharingapp.model.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewNotesViewModel @Inject constructor() : ViewModel() {

    val notesData = MutableLiveData<Notes>()


}