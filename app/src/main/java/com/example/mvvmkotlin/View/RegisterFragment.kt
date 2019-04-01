package com.example.mvvmkotlin.View

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmkotlin.Model.RegisterUser
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.ViewModel.RegisterViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment: Fragment() {
    private lateinit var ivProfilePic:ImageView
    private lateinit var ivChoosePic:ImageView
    private lateinit var textInputLayoutFirstName:TextInputLayout
    private lateinit var textInputLayoutLastName:TextInputLayout
    private lateinit var textInputLayoutEmail:TextInputLayout
    private lateinit var textInputLayoutPassword:TextInputLayout
    private lateinit var et_first_name:TextInputEditText
    private lateinit var et_last_name:TextInputEditText
    private lateinit var et_email:TextInputEditText
    private lateinit var et_password:TextInputEditText
    private lateinit var btnRegister:Button

//    private var bookingHistoryViewModel: BookingHistoryViewModel?=null
    private var registerViewModel : RegisterViewModel?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_register,container,false)
        initUI(view)
        setClickListener()

        registerViewModel=ViewModelProviders.of(this).get(RegisterViewModel::class.java)
//        registerViewModel?.getUserDetails()
        return view
    }

    private fun setClickListener() {
        btnRegister.setOnClickListener {
            if (isValidUser()) {
               /* var registerUser = RegisterUser(
                    et_first_name.text.toString(),
                    et_last_name.text.toString(),
                    et_email.text.toString(),
                    et_password.text.toString())*/

                registerViewModel?.getUserDetails()?.observe(this,object :Observer<RegisterUser>{
                    override fun onChanged(registerUser: RegisterUser?) {

                    }
                })
            }
        }
    }

    private fun isValidUser(): Boolean {
            if (et_first_name.text.toString().isEmpty()){
                return false
            }
            else if (et_last_name.text.toString().isEmpty()){
                return false
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches()){
                return false
            }
            else if (et_password.text.toString().length < 5){
                return false
            }
            return true
    }

    private fun initUI(view: View?) {
        ivProfilePic=view!!.findViewById(R.id.ivProfilePic)
        ivChoosePic=view!!.findViewById(R.id.ivChoosePic)
        textInputLayoutFirstName=view!!.findViewById(R.id.textInputLayoutFirstName)
        textInputLayoutLastName=view!!.findViewById(R.id.textInputLayoutLastName)
        textInputLayoutEmail=view!!.findViewById(R.id.textInputLayoutEmail)
        textInputLayoutPassword=view!!.findViewById(R.id.textInputLayoutPassword)
        et_first_name=view!!.findViewById(R.id.et_first_name)
        et_last_name=view!!.findViewById(R.id.et_last_name)
        et_email=view!!.findViewById(R.id.et_email)
        et_password=view!!.findViewById(R.id.et_password)
        btnRegister=view!!.findViewById(R.id.btnRegister)
    }
}