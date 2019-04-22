package com.example.mvvmkotlin.Services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.mvvmkotlin.util.Utility
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class TrackingService : Service(){
    override fun onBind(p0: Intent?): IBinder? {
        null!!
    }

    override fun onCreate() {
        super.onCreate()
        requestLocationUpdates()
    }

    private fun requestLocationUpdates() {
        var locationRequest : LocationRequest = LocationRequest()
        locationRequest.setInterval(10000)
        locationRequest.setFastestInterval(5000)
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        var client : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        Utility.getFirebaseDb().collection("User")
            .document()

    }

}