package com.example.mvvmkotlin.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmkotlin.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        setSupportActionBar(findViewById(R.id.toolbar))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
