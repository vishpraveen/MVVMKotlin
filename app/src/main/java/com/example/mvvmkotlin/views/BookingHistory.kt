package com.example.mvvmkotlin.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkotlin.models.BookingHistoryData
import com.example.mvvmkotlin.models.BookingHistoryModel
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.viewmodels.BookingHistoryViewModel

import kotlinx.android.synthetic.main.activity_booking_history.*

class BookingHistory : AppCompatActivity() {
    private val TAG:String=BookingHistory::class.java.simpleName
    private lateinit var bookingHistoryList :RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var bookingHistoryAdapter : BookingHistoryAdapter
    private val context: Context=this
    private var historyList: ArrayList<BookingHistoryData> = ArrayList()

    private var bookingHistoryViewModel: BookingHistoryViewModel?=null
    private var progressBar: ProgressBar?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_history)
        setSupportActionBar(toolbar)

        initUI()
        progressBar= ProgressBar(this)


//        calling the API to fetch bookingHistory data
        bookingHistoryViewModel=ViewModelProviders.of(this).get(BookingHistoryViewModel::class.java)
        bookingHistoryViewModel?.getbookinghistorymodel()?.observe(this,object : Observer<BookingHistoryModel>{
            override fun onChanged(bookinghistory: BookingHistoryModel?) {
                Log.e(TAG,"onChanged: "+bookinghistory.toString())
//                Toast.makeText(context, "onChanged: \n"+bookinghistory.toString(), Toast.LENGTH_SHORT).show();
                historyList.addAll(bookinghistory!!.bookingHistory)
                Log.e(TAG,"historyList: "+historyList.size)
                bookingHistoryAdapter.notifyDataSetChanged()
            }

        })

        bookingHistoryViewModel?.showhideProgressDialog()?.observe(this,object:Observer<Boolean>{
            override fun onChanged(status: Boolean) {

                Log.e(TAG,"status: "+status)
                if (status){
//                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                    progressBar!!.visibility= View.VISIBLE
                }
                else{
                    progressBar!!.visibility= View.GONE
                }
            }

        })

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }

    private fun initUI() {
        bookingHistoryList=findViewById(R.id.bookingHistoryList)
        progressBar=findViewById(R.id.progressBar)
        layoutManager= LinearLayoutManager(this)
        bookingHistoryList.layoutManager=layoutManager
        bookingHistoryAdapter= BookingHistoryAdapter(this,historyList)
        bookingHistoryList.adapter=bookingHistoryAdapter
    }

}
