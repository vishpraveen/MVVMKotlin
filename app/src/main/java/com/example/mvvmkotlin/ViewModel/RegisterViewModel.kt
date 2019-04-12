package com.example.mvvmkotlin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlin.Model.CommonModel
import com.example.mvvmkotlin.util.Utility
import com.google.firebase.auth.FirebaseAuth


class RegisterViewModel: ViewModel() {
    private val TAG:String=RegisterViewModel::class.java.simpleName
    private lateinit var auth: FirebaseAuth

    var first_name= MutableLiveData<String>()
    var last_name= MutableLiveData<String>()
    var email= MutableLiveData<String>()
    var password= MutableLiveData<String>()
    private var commonModel: CommonModel?=null
    var registerMutableLiveData: MutableLiveData<CommonModel>?=null
    var showHideLoader: MutableLiveData<Boolean>?= MutableLiveData()

    public fun setUserDetails(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        profileImage: String
    ): MutableLiveData<CommonModel> {
            registerMutableLiveData= MutableLiveData()
            registerUser(firstName,lastName,email,password,profileImage)
        return registerMutableLiveData as MutableLiveData<CommonModel>
    }

    public fun showhideProgressDialog(): LiveData<Boolean> {
        return showHideLoader as LiveData<Boolean>
    }

    public fun registerUser(firstName: String,
                             lastName: String,
                             email: String,
                             password: String,
                            profileImage: String){

        showHideLoader?.value=true

        val user = HashMap<String, Any>()
        user["firstName"] = firstName
        user["lastName"] = lastName
        user["email"] = email
        user["password"] = password
        user["profilePic"] = profileImage

        Utility.getfirebaseAuth().createUserWithEmailAndPassword(email,password).addOnCompleteListener {task ->
            if (task.isSuccessful){
                Log.e(TAG,"OnComplete: SignUp Success")
//                registerMutableLiveData.value?.status="1"
//                registerMutableLiveData.value?.message="Success"
                commonModel=CommonModel("1","Success")
                registerMutableLiveData?.value=commonModel
                addUserDetailsToDatabase(user)
//                Utility.getfirebaseAuth().signOut()
            }
            else{
                showHideLoader?.value=false
//                registerMutableLiveData.value?.status="0"
//                registerMutableLiveData.value?.message="Failed"
                commonModel=CommonModel("0","Failed")
                registerMutableLiveData?.value=commonModel
                // If sign in fails, display a message to the user.
                Log.e(TAG, "signInWithEmail:failure", task.exception)
            }
        }
            .addOnFailureListener { exception ->
                showHideLoader?.value=false
//                registerMutableLiveData.value?.status="0"
//                registerMutableLiveData.value?.message="Failed"
                commonModel=CommonModel("0","Failed")
                registerMutableLiveData?.value=commonModel
                Log.e(TAG,"OnFailure: "+exception.message)
            }
    }

    private fun addUserDetailsToDatabase(user: HashMap<String, Any>) {
        Utility.getFirebaseDb().collection("User")
//            db.collection("User")
            .add(user)
            .addOnSuccessListener { documentReference ->
                showHideLoader?.value=false
                Log.e(TAG,"DocumentSnapshot added with ID: ${documentReference.id}")

            }
            .addOnFailureListener { exception ->
                showHideLoader?.value=false
                Log.e(TAG,"Error adding document: "+ exception)
            }
    }


}