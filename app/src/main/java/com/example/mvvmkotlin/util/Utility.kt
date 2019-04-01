package com.example.mvvmkotlin.util

import com.google.firebase.firestore.FirebaseFirestore

object Utility {

    lateinit var firebaseDB:FirebaseFirestore

    public fun getFirebaseDb(): FirebaseFirestore {
        if (firebaseDB==null){
            firebaseDB= FirebaseFirestore.getInstance()
        }
        return firebaseDB
    }
}