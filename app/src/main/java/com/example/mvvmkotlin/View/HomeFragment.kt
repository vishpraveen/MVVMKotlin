package com.example.mvvmkotlin.View

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.example.mvvmkotlin.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import java.lang.Exception
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var mMapView: MapView
    private var mGoogleMap: GoogleMap?=null
    private var mLastKnownLocation: Location?=null
    private var isLocationPermissionGranted=false
    private val PERMISSION_REQUEST_LOCATION=10
    private var DEFAULT_ZOOM=18.0f
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private val sydney = LatLng(-34.0, 151.0)
    private lateinit var myLocation : CardView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view=inflater.inflate(R.layout.fragment_home,container,false)
        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(activity!!)
        var locationManager: LocationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(context, getString(R.string.enable_location), Toast.LENGTH_SHORT).show()
//            finish()
        }
        mMapView= view.findViewById(R.id.map)
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()
        try {
            MapsInitializer.initialize(activity?.applicationContext)
        }
        catch (e:Exception){
            Log.e(this@HomeFragment.javaClass.simpleName,e.localizedMessage)
        }
        mMapView.getMapAsync(object :OnMapReadyCallback{
            override fun onMapReady(gMap: GoogleMap?) {
                mGoogleMap=gMap
                mGoogleMap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(context,R.raw.map_style))
                mGoogleMap!!.uiSettings.isMyLocationButtonEnabled=false
                mGoogleMap!!.uiSettings.isRotateGesturesEnabled=false
                mGoogleMap!!.uiSettings.isScrollGesturesEnabled=true
                mGoogleMap!!.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom=false

                mGoogleMap!!.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener{
                    override fun onMarkerDragEnd(marker : Marker?) {
                        var position : LatLng =marker!!.position
                        getLocationDetails(position)
                    }

                    override fun onMarkerDragStart(marker : Marker?) {

                    }

                    override fun onMarkerDrag(marker : Marker?) {

                    }

                })

                updateLocationUI()

                getDeviceLocation()

            }

        })
        myLocation=view.findViewById(R.id.myLocation)
        myLocation.setOnClickListener {
            getMyLocation()
        }
        return view
    }

    private fun getLocationDetails(position: LatLng) {
        var geocoder = Geocoder(context, Locale.getDefault())

        var addresses : ArrayList<Address>
        addresses = geocoder.getFromLocation(position.latitude,position.longitude,1) as ArrayList<Address>
        var address =addresses[0].getAddressLine(0)
        var city = addresses[0].locality

        Toast.makeText(context, "New Address: $address $city", Toast.LENGTH_SHORT).show()
    }

    private fun getMyLocation() {
        var location : Location = mGoogleMap!!.myLocation
        var latlng : LatLng= LatLng(location.latitude,location.longitude)

        var cameraPosition : CameraPosition = CameraPosition.fromLatLngZoom(latlng,mGoogleMap!!.cameraPosition.zoom)
        mGoogleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun getDeviceLocation() {
        try {
            if (isLocationPermissionGranted){
                var locationResult=mFusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        if (task.result!=null) {
                            mLastKnownLocation = task.result
                            mGoogleMap!!.addMarker(MarkerOptions().position(LatLng(mLastKnownLocation!!.latitude, mLastKnownLocation!!.longitude))
                                .title("Marker at Current Location")
                                .draggable(true))?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                            mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mLastKnownLocation!!.latitude,
                                mLastKnownLocation!!.longitude), DEFAULT_ZOOM))
                        }
                        else{
                            Log.e(this@HomeFragment::class.java.simpleName,getString(R.string.task_is_null))
                            getDeviceLocation()
                        }
                    }
                    else{
                        Log.e("Exception: ","Current location is null. Using defaults.");
                        Log.e( "Exception: ", task.exception.toString());
                        mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,DEFAULT_ZOOM))
                        mGoogleMap!!.uiSettings?.isMyLocationButtonEnabled=false
                    }
                }
            }
        }catch (e:SecurityException){
            Log.e("Exception:",e.message )
        }
    }

    private fun updateLocationUI() {
        if (mGoogleMap==null){
            return
        }
        try {
            if (isLocationPermissionGranted){
                mGoogleMap!!.isMyLocationEnabled=true
                mGoogleMap!!.uiSettings?.isMyLocationButtonEnabled=false
//                startService for location
                startTrackerService()
            }
            else{
                mGoogleMap!!.isMyLocationEnabled=false
                mGoogleMap!!.uiSettings?.isMyLocationButtonEnabled=false
                mLastKnownLocation=null
                getLocationPermission()
            }
        }catch (e:SecurityException){
            Log.e("Map Activity Exception:", e.message)
        }
    }
//======================================================================================================================
//======================================Starting Location Tracking Service==============================================
//======================================================================================================================
    private fun startTrackerService() {

    }

    private fun getLocationPermission() {
        if (checkSelfPermission(context!!,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){

//            permission has been granted
            isLocationPermissionGranted=true
            updateLocationUI()
        }
        else{
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),PERMISSION_REQUEST_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        isLocationPermissionGranted=false

        when(requestCode){
            PERMISSION_REQUEST_LOCATION->{
                if (grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    isLocationPermissionGranted=true
                    updateLocationUI()
                }
                else{
                    Toast.makeText(context, getString(R.string.permission_not_granted), Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun initUI(view: View?) {

    }
}