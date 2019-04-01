package com.example.mvvmkotlin.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.mvvmkotlin.R

class LoginFragment : Fragment() {
    private lateinit var button_login:Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_login,container,false)
        button_login=view.findViewById(R.id.button_login)
        button_login.setOnClickListener {
            var intent= Intent(activity,BookingHistory::class.java)
            startActivity(intent)
        }

        return view
    }
}