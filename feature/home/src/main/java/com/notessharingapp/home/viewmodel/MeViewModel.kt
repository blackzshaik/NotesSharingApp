package com.notessharingapp.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.notessharingapp.data.HomeRemoteDataSource
import com.notessharingapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MeViewModel @Inject constructor(private val home:HomeRemoteDataSource): ViewModel() {

    val user : Flow<User?> = home.getUserData()
    fun getMyNotes() = home.getMyNotes().asLiveData()

}