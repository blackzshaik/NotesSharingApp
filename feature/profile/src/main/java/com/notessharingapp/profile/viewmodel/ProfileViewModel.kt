package com.notessharingapp.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notessharingapp.data.ProfileDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val profileDataSource = ProfileDataSource()

    var fullName = MutableLiveData("")
    var nickName = MutableLiveData("")
    var myNotesCount = MutableLiveData("")
    var sharedNotesCount = MutableLiveData("")

    init {
        viewModelScope.launch {
          profileDataSource.getMyProfile().collect{
              fullName.postValue(it.fullName)
              nickName.postValue("@"+it.nickName)
              myNotesCount.postValue(it.notes.size.toString())
              sharedNotesCount.postValue(it.sharedNotesCount.toString())
          }
        }
    }
}