package com.notessharingapp.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.notessharingapp.data.LoginDataSource
import com.notessharingapp.util.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() :ViewModel(), LoginDataSource.Listener{

    private val login  = LoginDataSource(this)
    var email = MutableLiveData("")
    var password = MutableLiveData("")

    var loginSuccess = MutableLiveData<Boolean>()
    var errorToast = MutableLiveData("")


    fun signIn(){
        println("Signin")
        if ((email.value != "").and(password.value != "") ){
            if (email.value.isValidEmail()){
                login.signIn(email.value!!,password.value!!)
            }else{
                errorToast.value = "Enter valid email address"
            }
        }else{
            errorToast.value = "Email or password is empty"
        }
    }

    override fun onLoginSuccess() {
        loginSuccess.postValue(true)
    }

    override fun onLoginFailed(reason: String) {
        loginSuccess.postValue(false)
        errorToast.value = reason
    }


}
