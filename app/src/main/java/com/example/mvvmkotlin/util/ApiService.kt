package com.example.mvvmkotlin.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    val baseUrl: String="http://sale24by7.com/playballnet_staging/index.php/api/";

    fun getBaseUrl() : APIService{
        val retrofit =Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(APIService::class.java)
    }
}