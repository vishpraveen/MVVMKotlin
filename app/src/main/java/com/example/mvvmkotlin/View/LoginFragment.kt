package com.example.mvvmkotlin.View

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airbnb.lottie.LottieAnimationView
import com.example.mvvmkotlin.Model.CommonModel
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.ViewModel.LoginViewModel
import com.example.mvvmkotlin.util.Utility
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {
    private var TAG: String = this@LoginFragment::class.java.simpleName
    private lateinit var loginMainConstraint: ConstraintLayout
    private lateinit var button_login: Button
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var et_email: TextInputEditText
    private lateinit var et_password: TextInputEditText
    private lateinit var animation_view: LottieAnimationView
    private var loginViewModel: LoginViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        initUI(view)
        setClickListener()
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        return view
    }

    private fun setClickListener() {
        button_login.setOnClickListener {
            if (Utility.isConnectedToInternet(context)) {
                if (Utility.getfirebaseAuth().currentUser != null) {
                    Utility.getfirebaseAuth().signOut()
                }
                if (isValidDetails()) {
                    loginViewModel?.getUser(et_email.text.toString().trim(), et_password.text.toString().trim())
                        ?.observe(this, object : Observer<CommonModel> {
                            override fun onChanged(commonModel: CommonModel?) {
                                Log.e(TAG, "onChanged: " + commonModel.toString())
                                msg(commonModel!!.message)
                            }
                        })

                    loginViewModel?.showhideProgressDialog()?.observe(this, object : Observer<Boolean> {
                        override fun onChanged(status: Boolean?) {
                            Log.e(TAG, "status: " + status)
                            if (status!!) {
                                showhideProgressbar(status)
                            } else {
                                showhideProgressbar(status)
                            }
                        }
                    })
                }
            } else {
                Utility.showShackBarWithoutAction(loginMainConstraint,getString(R.string.no_internet))
            }
        }
    }

    private fun showhideProgressbar(status: Boolean) {
        if (status) {
            animation_view.visibility = View.VISIBLE
        } else {
            animation_view.visibility = View.GONE
        }
    }

    private fun msg(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidDetails(): Boolean {
        textInputLayoutEmail.error = null
        textInputLayoutPassword.error = null

        if (!Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches()) {
            textInputLayoutEmail.error = getString(R.string.email_error)
            return false
        } else if (et_email.text.toString().isEmpty()) {
            textInputLayoutEmail.error = getString(R.string.email_empty)
            return false
        } else if (et_password.text.toString().isEmpty()) {
            textInputLayoutPassword.error = getString(R.string.password_empty)
            return false
        } else if (et_password.text.toString().isEmpty()) {
            textInputLayoutPassword.error = getString(R.string.password_empty)
            return false
        } else if (et_password.text.toString().length < 5) {
            textInputLayoutPassword.error = getString(R.string.password_length)
            return false
        }
        return true
    }

    private fun initUI(view: View?) {
        loginMainConstraint = view!!.findViewById(R.id.loginMainConstraint)
        textInputLayoutEmail = view!!.findViewById(R.id.textInputLayoutEmail)
        textInputLayoutPassword = view!!.findViewById(R.id.textInputLayoutPassword)
        et_email = view!!.findViewById(R.id.et_email)
        et_password = view!!.findViewById(R.id.et_password)
        button_login = view!!.findViewById(R.id.button_login)
        animation_view = view.findViewById(R.id.animation_view)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = Utility.getfirebaseAuth().currentUser
        Log.e(TAG, "AlreadySignedIn: " + currentUser.toString())
//        updateUI(currentUser)

    }
}