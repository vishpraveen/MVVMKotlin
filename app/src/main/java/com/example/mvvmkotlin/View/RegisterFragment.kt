package com.example.mvvmkotlin.View

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.mvvmkotlin.Model.CommonModel
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.ViewModel.RegisterViewModel
import com.example.mvvmkotlin.util.Utility
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.ByteArrayOutputStream
import java.io.InputStream

class RegisterFragment: Fragment() {
    private val TAG:String= RegisterFragment::class.java.simpleName
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
    private lateinit var animation_view: LottieAnimationView
    private var encodedImageString: String=""
    private val REQUEST_IMAGE_GET = 1
    private val REQUEST_IMAGE_CAPTURE = 2

//    private var bookingHistoryViewModel: BookingHistoryViewModel?=null
    private var registerViewModel : RegisterViewModel?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_register,container,false)
        initUI(view)
        setClickListener()
        registerViewModel=ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        return view
    }

    private fun setClickListener() {

        ivChoosePic.setOnClickListener {
            var optionDialog:AlertDialog
            val alertDialog: MaterialAlertDialogBuilder =MaterialAlertDialogBuilder(context,R.style.chooseImageTheme)
            val view=LayoutInflater.from(context).inflate(R.layout.dialog_custom,null)
            val ivGallery= view.findViewById<ImageView>(R.id.ivGallery)
            val ivCamera= view.findViewById<ImageView>(R.id.ivCamera)
            val llGallery= view.findViewById<LinearLayout>(R.id.llGallery)
            val llCamera= view.findViewById<LinearLayout>(R.id.llCamera)
            alertDialog.setView(view)
//            alertDialog.show()
            optionDialog=alertDialog.create()
            optionDialog.show()

            llGallery.setOnClickListener {
                optionDialog.dismiss()
                if (checkSelfPermission(context!!,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_IMAGE_GET)
                }
                else {
                    selectImage()
                }
            }
            llCamera.setOnClickListener {
                optionDialog.dismiss()
                if (checkSelfPermission(context!!,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                    requestPermissions(arrayOf(Manifest.permission.CAMERA),REQUEST_IMAGE_CAPTURE)
                }
                else {
                    captureImage()
                }
            }
        }



        btnRegister.setOnClickListener {
            if (isValidUser()) {
               registerViewModel?.setUserDetails(et_first_name.text.toString(),
                   et_last_name.text.toString(),
                   et_email.text.toString(),
                   et_password.text.toString(),
                   encodedImageString)?.observe(this,object: Observer<CommonModel>{
                   override fun onChanged(commonModel: CommonModel?) {
                       Log.e(TAG,"onChanged: "+commonModel.toString())
                       msg(commonModel?.message)
                   }

               })

                registerViewModel?.showhideProgressDialog()?.observe(this,
                    Observer<Boolean> { status ->
                        Log.e(TAG,"status: "+status)
                        if (status){
                            showhideProgressbar(status)
                        } else{
                            showhideProgressbar(status)
                        }
                    })
            Log.e(TAG,"onRegisterClick: ")
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_IMAGE_GET->{
                if (grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    selectImage()
                }
                else{
                    showPermissionDialog(getString(R.string.gallery_permission_title),getString(R.string.gallery_permission))
                }
            }
            REQUEST_IMAGE_CAPTURE->{
                if (grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    captureImage()
                }
                else{
                    showPermissionDialog(getString(R.string.camera_permission_title),getString(R.string.camera_permission))
                }
            }
        }
    }

    private fun showPermissionDialog(titleMessage:String, requestMessage: String) {
        var permissionDialog= MaterialAlertDialogBuilder(context)
        permissionDialog.setTitle(titleMessage)
        permissionDialog.setMessage(requestMessage)
        permissionDialog.setPositiveButton("Ok", null)
        permissionDialog.show()
    }

    private fun captureImage() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        if (intent.resolveActivity(activity!!.packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == Activity.RESULT_OK) {
//            val thumbnail: Bitmap = data!!.getParcelableExtra("data")
            val fullPhotoUri: Uri = data!!.data
            // Do work with photo saved at fullPhotoUri
            var imageStream:InputStream= activity!!.contentResolver.openInputStream(fullPhotoUri)
            var image:Bitmap= BitmapFactory.decodeStream(imageStream)
            encodedImageString=encodeImageToString(image)
            Log.e(TAG,"EncodedImage: "+encodedImageString)
            Glide.with(context!!)
                .asBitmap()
                .load(fullPhotoUri)
                .into(ivProfilePic)
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data!!.extras.get("data") as Bitmap
            encodedImageString=encodeImageToString(imageBitmap)
            Log.e(TAG,"EncodedImage: "+encodedImageString)
            Glide.with(context!!)
                .asBitmap()
                .load(imageBitmap)
                .into(ivProfilePic)
        }
    }

    private fun encodeImageToString(image: Bitmap): String {
        var baos= ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG,100,baos)
        var b = baos.toByteArray()
        var encImage=Base64.encodeToString(b,Base64.DEFAULT)
        return encImage
    }

    private fun msg(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
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
        Glide.with(this)
            .load(R.drawable.ic_profile)
            .into(ivProfilePic)
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
        animation_view=view.findViewById(R.id.animation_view)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = Utility.getfirebaseAuth().currentUser
        if (currentUser!=null){
            Utility.getfirebaseAuth().signOut()
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
}