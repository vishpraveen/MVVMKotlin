package com.example.mvvmkotlin.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkotlin.models.BookingHistoryModel
import com.example.mvvmkotlin.util.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingHistoryViewModel : ViewModel() {
    private val TAG:String=BookingHistoryModel::class.java.simpleName

    var bookinghistorymodel :MutableLiveData<BookingHistoryModel>?=null
    var showHideLoader: MutableLiveData<Boolean>?= MutableLiveData()

    public fun getbookinghistorymodel() : LiveData<BookingHistoryModel>{
            bookinghistorymodel = MutableLiveData<BookingHistoryModel>()
            fetchBookingHistoryData("2", "nfLo77EODdYk3CI2", "10", "1")
        return bookinghistorymodel as MutableLiveData<BookingHistoryModel>
    }
    public fun showhideProgressDialog(): LiveData<Boolean>{
        return showHideLoader as LiveData<Boolean>
    }


    public fun fetchBookingHistoryData(
        userId: String,
        userPrivateKey: String,
        limit: String,
        pagenumber: String){
        showHideLoader?.value=true

        ApiService.getBaseUrl()
            .bookingHistory(userId,userPrivateKey,limit,pagenumber)
            .enqueue(object :Callback<BookingHistoryModel>{
                override fun onFailure(call: Call<BookingHistoryModel>, t: Throwable) {
                    showHideLoader?.value=false
                    Log.e(TAG,"onFailure: "+t.message)
                }

                override fun onResponse(call: Call<BookingHistoryModel>, response: Response<BookingHistoryModel>) {
                    Log.e(TAG,"onResponse: "+response.isSuccessful+" ,"+ response.body().toString())
                    bookinghistorymodel?.value=response.body()
                    showHideLoader?.value=false
                }

            })
    }


}


