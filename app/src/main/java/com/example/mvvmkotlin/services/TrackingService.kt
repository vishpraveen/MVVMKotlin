package com.example.mvvmkotlin.services

import android.Manifest
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mvvmkotlin.util.SharedPreferenceKeys
import com.example.mvvmkotlin.util.Utility
import com.google.android.gms.location.*

class TrackingService : Service(){
    val TAG: String=TrackingService::class.java.simpleName
    override fun onBind(p0: Intent?): IBinder? {
        null!!
    }

    override fun onCreate() {
        super.onCreate()
        requestLocationUpdates()
    }

    private fun requestLocationUpdates() {
        val locationRequest : LocationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY
        val client : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        /**
        * Getting Users Location
        * */
        val permission : Int = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        if (permission == PackageManager.PERMISSION_GRANTED){
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(locationRequest,object : LocationCallback(){
                override fun onLocationResult(result: LocationResult?) {
                    super.onLocationResult(result)
                    val location : Location = result!!.lastLocation
                    if (location!=null){
                        Utility.getFirebaseDb().collection("User")
                            .document("Locations")
                            .collection(Utility.getPreference(applicationContext,SharedPreferenceKeys().email).toString())
                                .document("location")
                                .set(location)
                                .addOnSuccessListener {
                                    Log.e(TAG,"DocumentSnapshot successfully written!: "+location.latitude+", "+location.longitude)
//                                msg("Service Started.")
                                }
                                .addOnFailureListener { exception ->
                                    Log.e(TAG,"Error writing document: "+exception.localizedMessage)
//                                msg("Failed to start Service")
                                }
                    }
                }
            },null)
        }
    }

    private fun msg(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    /*
  * Creating a BroadCast Receiver
  * */
    protected var stopReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e(TAG,"stop")
            // Stop the service when the notification is tapped
            unregisterReceiver(this)
//            LocalBroadcastManager.getInstance(context!!).unregisterReceiver(this)
            stopSelf()
        }
    }

}