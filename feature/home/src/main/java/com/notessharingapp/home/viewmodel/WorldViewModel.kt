package com.notessharingapp.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.notessharingapp.data.HomeRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorldViewModel @Inject constructor(private val homeRemoteDataSource: HomeRemoteDataSource): ViewModel() {

    val notesFromWorld = homeRemoteDataSource.getNotesFromWorld().asLiveData()
}