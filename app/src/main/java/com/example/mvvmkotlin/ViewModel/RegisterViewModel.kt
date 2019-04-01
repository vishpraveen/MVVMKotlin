package com.example.mvvmkotlin.ViewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlin.Model.RegisterUser

class RegisterViewModel: ViewModel() {
    var first_name= MutableLiveData<String>()
    var last_name= MutableLiveData<String>()
    var email= MutableLiveData<String>()
    var password= MutableLiveData<String>()

    var registerMutableLiveData= MutableLiveData<RegisterUser>()

    public fun getUserDetails(): MutableLiveData<RegisterUser> {
        if (registerMutableLiveData==null){
            registerMutableLiveData= MutableLiveData()

        }
        return registerMutableLiveData
    }

    public fun registerClick(view: View){
        var registerUser=RegisterUser(first_name.value.toString(),last_name.value.toString(),email.value.toString(),password.value.toString())

        registerMutableLiveData.value=registerUser
    }
}