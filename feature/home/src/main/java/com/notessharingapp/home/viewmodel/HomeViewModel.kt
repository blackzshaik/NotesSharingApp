package com.notessharingapp.home.viewmodel

import androidx.lifecycle.ViewModel
import com.notessharingapp.data.HomeRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeDataSource: HomeRemoteDataSource) :ViewModel() {

    var isLoggedIn = homeDataSource.isLoggedIn()


}