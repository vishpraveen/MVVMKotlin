package com.example.mvvmkotlin.models

import android.util.Patterns

data class LoginUser(var emailAddress : String,
                     var password: String) {
    public fun isEmailValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
    }

    public fun isPasswordGreaterThan5(): Boolean{
        return password.length > 5
    }
}
