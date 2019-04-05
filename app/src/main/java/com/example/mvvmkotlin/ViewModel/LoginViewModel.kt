package com.example.mvvmkotlin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlin.Model.CommonModel
import com.example.mvvmkotlin.util.Utility

class LoginViewModel : ViewModel() {
    val TAG: String= LoginViewModel::class.java.simpleName
    var loginMutableLiveData: MutableLiveData<CommonModel>?=null
    var showHideLoader: MutableLiveData<Boolean>?= MutableLiveData()
    var commonModel: CommonModel?=null

    public fun getUser(email: String , password: String): MutableLiveData<CommonModel>{
        if (loginMutableLiveData == null){
            loginMutableLiveData = MutableLiveData()
            loginClick(email,password)
        }
        return loginMutableLiveData as MutableLiveData<CommonModel>
    }

    public fun showhideProgressDialog(): LiveData<Boolean> {
        return showHideLoader as LiveData<Boolean>
    }

    public fun loginClick(email: String,
                          password:String) {
//        var loginUser =LoginUser(emailAddress.value.toString(),password.value.toString())
//        loginMutableLiveData.value=loginUser
        showHideLoader?.value = true

        Utility.getfirebaseAuth().signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showHideLoader?.value = false
                commonModel=CommonModel("1","Success")
                loginMutableLiveData?.value=commonModel
                val user = Utility.getfirebaseAuth().currentUser
            } else {
                showHideLoader?.value = false
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                commonModel=CommonModel("0","Failure: \n"+task.exception)
                loginMutableLiveData?.value=commonModel
            }
        }
            .addOnFailureListener { exception ->
                showHideLoader?.value = false
                Log.w(TAG, "signInWithEmail:failure"+ exception.localizedMessage)
                commonModel=CommonModel("0","Failure: \n"+exception.localizedMessage)
                loginMutableLiveData?.value=commonModel
            }
    }
}