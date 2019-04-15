package com.example.mvvmkotlin.views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.util.SharedPreferenceKeys
import com.example.mvvmkotlin.util.Utility
import kotlinx.android.synthetic.main.adapter_bookinghistory.*

class ProfileFragment :Fragment(){
    private lateinit var IvProfile: ImageView
    private lateinit var CollapseImage: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view : View = inflater.inflate(R.layout.fragment_profile, container, false)
        IvProfile=view.findViewById(R.id.ivprofile)
        CollapseImage=view.findViewById(R.id.collapseImage)
        Glide.with(context!!)
            .load(decodeStringToBitmap(Utility.getPreference(context,SharedPreferenceKeys().profilePic)))
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_place_holder))
            .into(CollapseImage)

        IvProfile.setImageResource(R.drawable.ic_profile)
        Glide.with(context!!)
            .load(decodeStringToBitmap(Utility.getPreference(context,SharedPreferenceKeys().profilePic)))
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_place_holder))
            .into(IvProfile)
        return view
    }

    private fun decodeStringToBitmap(stringImage: Any?) : Bitmap {
        var decodeString= Base64.decode(stringImage.toString(), Base64.DEFAULT)
        var image: Bitmap = BitmapFactory.decodeByteArray(decodeString,0,decodeString.size)
        return image
    }

}
