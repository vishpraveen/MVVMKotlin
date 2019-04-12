package com.example.mvvmkotlin.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlin.Model.UserDetails
import com.example.mvvmkotlin.util.SharedPreferenceKeys
import com.example.mvvmkotlin.util.Utility
import com.google.firebase.firestore.CollectionReference

class DrawerActivityViewModel: ViewModel() {
    private val TAG= DrawerActivityViewModel::class.java.simpleName
    private var currentUser: CollectionReference?=null
    private var userDetails:UserDetails?=null
    private var drawerMutableLiveData: MutableLiveData<UserDetails>?=null

    public fun getUserDetails(context: Context): MutableLiveData<UserDetails>{
            drawerMutableLiveData= MutableLiveData()
            fetchUserDetails(context)
        return drawerMutableLiveData as MutableLiveData<UserDetails>
    }

    private fun fetchUserDetails(context: Context) {
            currentUser= Utility.getFirebaseDb().collection("User")
            Log.e(TAG,"SharedPref: "+Utility.getPreference(context,SharedPreferenceKeys().email))
            currentUser!!.whereEqualTo("email",Utility.getPreference(context,SharedPreferenceKeys().email))
                .get()
                .addOnSuccessListener {querySnapshot ->
                    if (querySnapshot.isEmpty){
                        Log.e(TAG,"No Data Found")
                    }
                    else{
//                        Log.e(TAG,"Data Found"+querySnapshot.documents.toString())
//                        Log.e(TAG,"MetaData: "+querySnapshot.metadata.toString())
                        for (docs in querySnapshot.documents) {
                            userDetails= UserDetails("1","Success",docs.get("firstName").toString(), docs.get("lastName").toString(), docs.get("email").toString(), docs.get("profilePic").toString())
                            drawerMutableLiveData!!.value=userDetails

                           /* Log.e(TAG,"email: "+docs.get("email"))
                            Log.e(TAG,"firstName: "+docs.get("firstName"))
                            Log.e(TAG,"lastName: "+docs.get("lastName"))
                            Log.e(TAG,"password: "+docs.get("password"))
                            Log.e(TAG,"profilePic: "+docs.get("profilePic"))*/
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Error getting documents: ", exception)
                    userDetails= UserDetails("0",exception.localizedMessage,"","","","")
                    drawerMutableLiveData!!.value=userDetails
                }
    }
}