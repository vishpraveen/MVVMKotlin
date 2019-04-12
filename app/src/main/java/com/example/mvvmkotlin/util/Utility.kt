package com.example.mvvmkotlin.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.View
import androidx.core.content.getSystemService
import com.example.mvvmkotlin.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object Utility {


    public fun getFirebaseDb(): FirebaseFirestore {
            val firebaseDB:FirebaseFirestore = FirebaseFirestore.getInstance()
        return firebaseDB
    }

    public fun getfirebaseAuth(): FirebaseAuth{
        val firebaseAuth:FirebaseAuth= FirebaseAuth.getInstance()
        return firebaseAuth
    }

    public fun isConnectedToInternet(context: Context?):Boolean{
        val connectivityManage=context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo?=connectivityManage.activeNetworkInfo
        var isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        Log.e("isConnectedToInternet","Status: "+ isConnected)
        return isConnected
    }

    /**
     * @param rootView: pass layout parent view id.
     * @param message: message needs to be displayed.
     * */
    public fun showShackBarWithoutAction(rootView: View,message:String){
        Snackbar.make(rootView,message,Snackbar.LENGTH_SHORT).show()
    }

    /**
     * @param rootView: pass layout parent view id.
     * @param message: message needs to be displayed.
     * */
    public fun showShackBarWithAction(rootView: View,message:String){
        Snackbar.make(rootView,message,Snackbar.LENGTH_LONG)
            .setAction("OK", View.OnClickListener {

            }).show()
    }

    public fun setPreference(context: Context?,key: String, value: String){
        var editor: SharedPreferences.Editor= context!!.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE).edit()
        editor.putString(key,value)
        editor.apply()
        editor.commit()
    }

    public fun setBooleanPreference(context: Context?,key: String, value: Boolean){
        var editor: SharedPreferences.Editor= context!!.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE).edit()
        editor.putBoolean(key,value)
        editor.apply()
        editor.commit()
    }

    public fun getPreference(context: Context?, key: String): String? {
        var sharedPref: SharedPreferences=context!!.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)
        return sharedPref.getString(key, "")
    }

    public fun getBoolenPreference(context: Context?, key: String): Boolean? {
        var sharedPref: SharedPreferences=context!!.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)
        return sharedPref.getBoolean(key, false)
    }

    public fun removePreference(context: Context?, key: String){
        var sharedPref: SharedPreferences=context!!.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor= sharedPref.edit()
        editor.remove(key)
        editor.apply()
        editor.commit()
    }

}