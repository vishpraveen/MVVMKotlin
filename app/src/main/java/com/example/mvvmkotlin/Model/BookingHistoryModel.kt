package com.example.mvvmkotlin.Model

import com.google.gson.annotations.SerializedName

data class BookingHistoryModel(
    var status: String,
    var message: String,
    var bookingHistory: ArrayList<BookingHistoryData>,
    var itemremaining: Int?,
    var nextpagenumber: Int?
)
//{
    /*@SerializedName("status")
    private lateinit var status: String
    @SerializedName("message")
    private lateinit var message: String
    @SerializedName("bookingHistory")
    private lateinit var bookingHistory: ArrayList<BookingHistoryData>
    @SerializedName("itemremaining")
    private var itemremaining : Int?=null
    @SerializedName("nextpagenumber")
    private var nextpagenumber : Int?=null*/


//}

data class BookingHistoryData (
    var bookingId: String,
    var bookingRequestId: String,
    var sportName: String,
    var bookingDate: String,
    var bookingStatus: String,
    var profileName: String,
    var profileImage: String,
    var address: String,
    var location: String,
    var trainingtime: String
) /*{
*//* @SerializedName("bookingId")
     private lateinit var bookingId: String
     @SerializedName("bookingRequestId")
     private lateinit var bookingRequestId: String
     @SerializedName("sportName")
     private lateinit var sportName: String
     @SerializedName("bookingDate")
     private lateinit var bookingDate: String
     @SerializedName("bookingStatus")
     private lateinit var bookingStatus: String
     @SerializedName("profileName")
     private lateinit var profileName: String
     @SerializedName("profileImage")
     private lateinit var profileImage: String
     @SerializedName("address")
     private lateinit var address: String
     @SerializedName("location")
     private lateinit var location: String
     @SerializedName("trainingtime")
     private lateinit var trainingtime: String*//*

}*/
