package com.example.mvvmkotlin.Model

import android.util.Patterns

data class UserDetails(var status: String,
                       var message: String,
                       var first_name: String,
                  var last_name: String,
                  var email: String,
                  var profilePic: String) {

    /*public fun isValidaDetails():Boolean{
        if (first_name.isEmpty()){
            return false
        }
        else if (last_name.isEmpty()){
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false
        }
        else if (password.length < 5){
            return false
        }
        return true
    }*/

}