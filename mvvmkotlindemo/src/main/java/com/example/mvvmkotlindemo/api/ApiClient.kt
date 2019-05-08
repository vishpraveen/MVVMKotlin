package com.example.mvvmkotlindemo.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val instance: ApiService = Retrofit.Builder().run {
        val gson= GsonBuilder()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create()
        baseUrl("https://api.github.com/")
        addConverterFactory(GsonConverterFactory.create(gson))
        build()
    }.create(ApiService::class.java)
}