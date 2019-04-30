package com.example.mvvmkotlin.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlin.util.Utility

class HomeFragmentViewModel:ViewModel() {
    private val TAG= HomeFragmentViewModel::class.java.simpleName
    private var markerList: MutableLiveData<Any>?=null

    public fun getMarkers():MutableLiveData<List<Any>>{
        markerList= MutableLiveData()
        fetchMarkers()
        return markerList as MutableLiveData<List<Any>>
    }

    private fun fetchMarkers() {
        Utility.getFirebaseDb().collection("User").document("Locations")
            .get()
            .addOnSuccessListener { document ->
                if (document!=null){
                    Log.e(TAG, "Data available: ${document.data.toString()}")
//                    markerList!!.value=document.data
                }
                else{
                    Log.e(TAG, "No Data Found.")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "get failed with ", exception)
            }
    }
}