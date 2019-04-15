package com.example.mvvmkotlin.util

import com.example.mvvmkotlin.models.BookingHistoryModel
import com.example.mvvmkotlin.models.CommonModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitApis {

    @GET("bookingHistory")
    fun bookingHistory(@Query("userId") userId:String,
                       @Query("userPrivateKey") userPrivateKey:String,
                       @Query("limit") limit:String,
                       @Query("pagenumber") pagenumber:String) : Call<BookingHistoryModel>

    @POST("registerUser")
    fun registerUser(@Field("firstName") firstName: String,
                     @Field("lastName") lastName: String,
                     @Field("email") email: String,
                     @Field("password") password: String) : Call<CommonModel>

}
