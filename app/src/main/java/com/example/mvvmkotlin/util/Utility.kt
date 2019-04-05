package com.example.mvvmkotlin.util

import android.content.Context
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
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
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


}