package com.example.mvvmkotlin.views

import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mvvmkotlin.models.UserDetails
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.viewmodels.DrawerActivityViewModel
import com.example.mvvmkotlin.util.SharedPreferenceKeys
import com.example.mvvmkotlin.util.Utility
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class DrawerActivity : AppCompatActivity() {

    private val TAG= DrawerActivity::class.java.simpleName
    private var context: Context? = null
    private var doubleBackToExitPressedOnce= false
    //    setting Navigation Components
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var navigationview: NavigationView
    private lateinit var fab: FloatingActionButton
    private lateinit var mainFrame: FrameLayout
    private lateinit var navigationHeader: View
    private lateinit var iv_image: ImageView
    private lateinit var tv_name: TextView
    private lateinit var tv_email: TextView
    private var homeFragment : HomeFragment = HomeFragment()
    private var profileFragment: ProfileFragment = ProfileFragment()
    private var fragmentManager: FragmentManager=supportFragmentManager
    private var drawerActViewModel: DrawerActivityViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        context = this
        initUI()
        setonClickListener()
        setHomeFragment()
        drawerActViewModel= ViewModelProviders.of(this).get(DrawerActivityViewModel::class.java)
        fetchUserDetails()
    }

    private fun fetchUserDetails() {
        if(Utility.isConnectedToInternet(context)){
            drawerActViewModel!!.getUserDetails(context!!).observe(this,Observer<UserDetails>{userDetails->
                if (userDetails.status.equals("1")){
                    tv_name.text=userDetails.first_name
                    tv_email.text=userDetails.email
                    if(!userDetails.profilePic.isEmpty()) {
                        Glide.with(context!!)
                            .asBitmap()
                            .load(decodeStringToBitmap(userDetails.profilePic))
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.ic_place_holder)
                            )
                            .into(iv_image)
                    }
                    Utility.setPreference(context,SharedPreferenceKeys().firstName,userDetails.first_name)
                    Utility.setPreference(context,SharedPreferenceKeys().lastName,userDetails.last_name)
                    Utility.setPreference(context,SharedPreferenceKeys().profilePic,userDetails.profilePic)
                }
                else{
                    Utility.showShackBarWithoutAction(drawerLayout,userDetails.message)
                }
            })
        }
        else{
            showErrorMessage(getString(R.string.no_internet))
        }
    }

    private fun decodeStringToBitmap(stringImage: Any?) : Bitmap{
        var decodeString= Base64.decode(stringImage.toString(),Base64.DEFAULT)
        var image: Bitmap= BitmapFactory.decodeByteArray(decodeString,0,decodeString.size)
        return image
    }

    private fun showErrorMessage(message: String) {
        Utility.showShackBarWithoutAction(drawerLayout,message)
    }


    private fun setHomeFragment() {
        addFragmentToActivity(fragmentManager,homeFragment, R.id.mainFrame)
    }

    private fun setonClickListener() {
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
//      Click events when clicked on navigation options
        navigationview.setNavigationItemSelectedListener { menuItem: MenuItem ->
            //         set item as selected to persist highlight

//            close the drawer when it is tapped
            drawerLayout.closeDrawer(GravityCompat.START)
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            when (menuItem.itemId) {
                R.id.home_fragment -> {
                    addFragmentToActivity(fragmentManager,homeFragment, R.id.mainFrame)
                    menuItem.isChecked = true
                    toolbar.title=getString(R.string.home)
                    msg(getString(R.string.home))
                } R.id.profile_fragment -> {
                addFragmentToActivity(fragmentManager,profileFragment, R.id.mainFrame)
                menuItem.isChecked = true
                toolbar.title=getString(R.string.profile_frag)
                msg(getString(R.string.profile_frag))
            }
                R.id.profile_activity -> {
                   /* val intent = Intent(this, MyProfileActivity::class.java)
                    startActivity(intent)
//                    addFragmentToActivity(fragmentManager,profileFragment,R.id.mainFrame)
//                    toolbar.title=getString(R.string.profile)
                    msg(getString(R.string.profile_act))*/
                }
                R.id.logout ->{
                    Utility.getfirebaseAuth().signOut()
                    Utility.removePreference(context,SharedPreferenceKeys().email)
                    Utility.removePreference(context,SharedPreferenceKeys().firstName)
                    Utility.removePreference(context,SharedPreferenceKeys().lastName)
                    Utility.removePreference(context,SharedPreferenceKeys().profilePic)
                    var intent=Intent(this@DrawerActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
    }

    private fun msg(message: String) {
        Toast.makeText(this@DrawerActivity, message, Toast.LENGTH_SHORT).show();
    }

    private fun initUI() {
        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar =findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.home)
//        title= toolbar.findViewById(R.id.toolbar_title)
//        title.text=getString(R.string.home)
        toolbar.inflateMenu(R.menu.notification_count)
        setSupportActionBar(toolbar)
        navigationview = findViewById(R.id.navigation)
        bottomAppBar = findViewById(R.id.bar)
        mainFrame = findViewById(R.id.mainFrame)
        val actionBar : ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        navigationHeader= navigationview.getHeaderView(0)
        iv_image=navigationHeader.findViewById(R.id.iv_image)
        tv_name=navigationHeader.findViewById(R.id.tv_name)
        tv_email=navigationHeader.findViewById(R.id.tv_email)

//        tv_name.text=currentUser
//        tv_email.text=currentUser!!.email
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notification_count,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.notification ->{
                msg("Clicked on Notification")
            }
        }
        return true
    }

    //    Adding fragment
    fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment, fragmentId: Int){
        val transition: FragmentTransaction = manager.beginTransaction()
        transition.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
        transition.replace(fragmentId,fragment)
        transition.commit()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce=true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
