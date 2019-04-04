package com.example.mvvmkotlin.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    val baseUrl: String="http://sale24by7.com/playballnet_staging/index.php/api/";
    lateinit var retrofit :Retrofit
    lateinit var retrofitForPOST :Retrofit
    fun getBaseUrl() : RetrofitApis{
        retrofit =Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RetrofitApis::class.java)
    }

    fun setBaseUrlForFirebase(dbUrl: String): RetrofitApis{
        retrofitForPOST=Retrofit.Builder()
            .baseUrl(dbUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitForPOST.create(RetrofitApis::class.java)
    }
}