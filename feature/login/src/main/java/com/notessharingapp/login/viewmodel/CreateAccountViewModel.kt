package com.notessharingapp.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.notessharingapp.data.LoginDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor() : ViewModel(), LoginDataSource.Listener {

    private val login = LoginDataSource(this)

    var fullName = MutableLiveData("")
    var nickName = MutableLiveData("")
    var email = MutableLiveData("")
    var password = MutableLiveData("")
    var confirmPassword = MutableLiveData("")

    var loginSuccess = MutableLiveData<Boolean>()


    fun createAccount() {
        if (fullName.value != "" &&
            nickName.value != "" &&
            email.value != "" &&
            password.value != "" &&
            confirmPassword.value != "" &&
            password.value ==
            confirmPassword.value) {

            login.createAccount(fullName.value!!, nickName.value!!, email.value!!, password.value!!)
        }else{
            println("account not created")
        }

    }

    override fun onLoginSuccess() {
        println("LOOOOOGGIINN SUUCECSSS")
        loginSuccess.postValue(true)
    }

    override fun onLoginFailed(reason: String) {
        loginSuccess.postValue(false)
        println("===> $reason")
    }
}
