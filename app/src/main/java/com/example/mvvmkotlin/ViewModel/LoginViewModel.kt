package com.example.mvvmkotlin.ViewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlin.Model.LoginUser

class LoginViewModel : ViewModel() {
    var emailAddress= MutableLiveData<String>();
    var password= MutableLiveData<String>();

    var loginMutableLiveData = MutableLiveData<LoginUser>()

    public fun getUser(): MutableLiveData<LoginUser>{
        if (loginMutableLiveData == null){
            loginMutableLiveData = MutableLiveData()
        }
        return loginMutableLiveData
    }

    public fun loginClick(view : View){
        var loginUser =LoginUser(emailAddress.value.toString(),password.value.toString())

        loginMutableLiveData.value=loginUser
    }
}