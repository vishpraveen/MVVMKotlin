package com.example.mvvmkotlin.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlin.models.MarkerLocation
import com.example.mvvmkotlin.util.Utility
import com.google.firebase.firestore.*

class HomeFragmentViewModel:ViewModel() {
    private val TAG= HomeFragmentViewModel::class.java.simpleName
    private var markers=ArrayList<MarkerLocation>()
    private var markerList: MutableLiveData<ArrayList<MarkerLocation>>?=null

    public fun getMarkers(email: String?):MutableLiveData<ArrayList<MarkerLocation>>{
        markerList= MutableLiveData()
//        addMarker(email)
        fetchMarkers()
        return markerList as MutableLiveData<ArrayList<MarkerLocation>>
    }

    private fun addMarker(email: String?) {
        Utility.getFirebaseDb().collection("Locations")
            .document(email.toString())
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

    private fun fetchMarkers() {
        val location=Utility.getFirebaseDb().collection("Locations")
        location.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot!=null){

                    Log.e(TAG,"Fetched Locations Size: ${querySnapshot.size()}")
                    Log.e(TAG,"Location Results: "+querySnapshot.documents.toString())
                    for (item in querySnapshot.documents){
                        Log.e(TAG,"Single Latitude: "+item.data!!.get("latitude"))
                        Log.e(TAG,"Single Longitude: "+item.data!!.get("longitude"))
                        val markerLocation=MarkerLocation(item.data!!.get("latitude").toString(),item.data!!.get("latitude").toString())
                        markers.add(markerLocation)
                    }
                    markerList!!.value=markers
                    Log.e(TAG, "MarkerList Size: "+markerList!!.value!!.size)
                }
            }
            .addOnFailureListener {
                Log.e(TAG,"Error On marker Fetch: $it")
            }



        /*val reff=Utility.getFirebaseDb().collection("Locations");
        reff.addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(quesrSnaoshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                if (!quesrSnaoshot!!.isEmpty){
                    maxCount= quesrSnaoshot.count()
                    Log.e(TAG,"Max Count: $maxCount")
                    addLocation()
                }
                else{
                    Log.e(TAG,"QueryShot is Empty")
                    maxCount=1
                    addLocation()
                }

                if (exception!=null){
                    Log.e(TAG,"Failed Error: $exception")
                }

            }
        })*/

    }

    /*private fun addLocation() {
        Utility.getFirebaseDb().collection("Locations").document(maxCount.toString())
    }*/
}