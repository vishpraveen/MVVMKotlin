package com.example.mvvmkotlin.util

import com.example.mvvmkotlin.Model.BookingHistoryModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface APIService {

    @GET("bookingHistory")
    fun bookingHistory(@Query("userId") userId:String,
                       @Query("userPrivateKey") userPrivateKey:String,
                       @Query("limit") limit:String,
                       @Query("pagenumber") pagenumber:String) : Call<BookingHistoryModel>

}
