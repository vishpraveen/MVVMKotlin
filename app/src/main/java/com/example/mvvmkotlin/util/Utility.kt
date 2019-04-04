package com.example.mvvmkotlin.util

import android.content.Context
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
}