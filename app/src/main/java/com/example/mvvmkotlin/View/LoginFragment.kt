package com.example.mvvmkotlin.View

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.util.Utility
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var TAG: String=this@LoginFragment::class.java.simpleName
    private lateinit var button_login:Button
    private lateinit var textInputLayoutEmail:TextInputLayout
    private lateinit var textInputLayoutPassword:TextInputLayout
    private lateinit var et_email:TextInputEditText
    private lateinit var et_password:TextInputEditText
    private lateinit var animation_view:LottieAnimationView

//    defining firebaseauth object
//    private lateinit var auth: FirebaseAuth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_login,container,false)
//        auth= FirebaseAuth.getInstance()
        initUI(view)
        setClickListener()
        return view
    }

    private fun setClickListener() {
        button_login.setOnClickListener {
            if (Utility.getfirebaseAuth().currentUser!=null){
                Utility.getfirebaseAuth().signOut()
            }
            if (isValidDetails()){
                showhideProgressbar(true)
                loginUser()
            }
        }
    }

    private fun showhideProgressbar(status: Boolean) {
        if (status){
            animation_view.visibility=View.VISIBLE
        }
        else{
            animation_view.visibility=View.GONE
        }
    }

    private fun loginUser() {
//        creating a new user
        Utility.getfirebaseAuth().signInWithEmailAndPassword(et_email.text.toString().trim(),
            et_password.text.toString().trim()).addOnCompleteListener { task ->
            if (task.isSuccessful){
                showhideProgressbar(false)
                msg(getString(R.string.success))
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val user = Utility.getfirebaseAuth().currentUser
//                updateUI(user)
            }
            else{
                showhideProgressbar(false)
                msg(getString(R.string.oops))
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(context, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
//                updateUI(null)
            }
        }

        /*Utility.getFirebaseDb().collection("User")
            .get()
            .addOnSuccessListener { result->
                for (document in result){
                    Log.e(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents.", exception)
            }*/
    }

    private fun msg(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidDetails(): Boolean {
        textInputLayoutEmail.error=null
        textInputLayoutPassword.error=null

        if (!Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches()){
            textInputLayoutEmail.error=getString(R.string.email_error)
            return false
        }
        else if (et_email.text.toString().isEmpty()){
            textInputLayoutEmail.error=getString(R.string.email_empty)
            return false
        }
        else if (et_password.text.toString().isEmpty()){
            textInputLayoutPassword.error=getString(R.string.password_empty)
            return false
        }
        else if (et_password.text.toString().isEmpty()){
            textInputLayoutPassword.error=getString(R.string.password_empty)
            return false
        }
        else if (et_password.text.toString().length<5){
            textInputLayoutPassword.error=getString(R.string.password_length)
            return false
        }
        return true
    }

    private fun initUI(view: View?) {
        textInputLayoutEmail=view!!.findViewById(R.id.textInputLayoutEmail)
        textInputLayoutPassword=view!!.findViewById(R.id.textInputLayoutPassword)
        et_email=view!!.findViewById(R.id.et_email)
        et_password=view!!.findViewById(R.id.et_password)
        button_login=view!!.findViewById(R.id.button_login)
        animation_view=view.findViewById(R.id.animation_view)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = Utility.getfirebaseAuth().currentUser
        Log.e(TAG,"AlreadySignedIn: "+currentUser.toString())
//        updateUI(currentUser)

    }
}