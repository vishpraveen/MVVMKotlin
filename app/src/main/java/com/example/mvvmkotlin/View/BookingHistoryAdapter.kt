package com.example.mvvmkotlin.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.example.mvvmkotlin.Model.BookingHistoryData
import com.example.mvvmkotlin.R

class BookingHistoryAdapter(
    var context: Context,
    var historyList: ArrayList<BookingHistoryData>
) :
    RecyclerView.Adapter<BookingHistoryAdapter.BookingHistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingHistoryViewHolder {
        val view : View= LayoutInflater.from(context).inflate(R.layout.adapter_bookinghistory,parent,false)
        return BookingHistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder: BookingHistoryViewHolder, position: Int) {
        holder.sportName.setText(historyList[position].sportName)
        holder.bookingDate.setText(historyList[position].bookingDate)
        holder.bookingId.setText(historyList[position].bookingId)
        holder.bookingTime.setText(historyList[position].trainingtime)
        holder.address.setText(historyList[position].address)
        holder.status.setText(historyList[position].bookingStatus)
        holder.profileName.setText(historyList[position].profileName)
        Glide.with(context)
            .asBitmap()
            .load(historyList[position].profileImage)
            .apply(RequestOptions().placeholder(R.drawable.ic_place_holder))
            .into(holder.ivProfile)
    }


    class BookingHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var sportName: EditText=itemView.findViewById(R.id.sportName);
        var bookingDate: EditText=itemView.findViewById(R.id.bookingDate);
        var bookingId: EditText=itemView.findViewById(R.id.bookingId);
        var bookingTime: EditText=itemView.findViewById(R.id.bookingTime);
        var address: EditText=itemView.findViewById(R.id.address);
        var status: EditText=itemView.findViewById(R.id.status);
        var profileName: EditText=itemView.findViewById(R.id.profileName);
        var ivProfile: ImageView=itemView.findViewById(R.id.ivProfile);
    }
}
