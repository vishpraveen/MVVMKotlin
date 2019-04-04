package com.example.mvvmkotlin.View

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.mvvmkotlin.Model.LoginUser
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.ViewModel.LoginViewModel
import com.google.android.material.tabs.TabLayout
import com.google.firebase.FirebaseApp
import java.util.*

class MainActivity : AppCompatActivity() {

    private var loginViewModel : LoginViewModel? =null

    private lateinit var pager : ViewPager
    private lateinit var tabs : TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        initUI()

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel?.getUser()?.observe(this,object: Observer<LoginUser>{
            override fun onChanged(loginUser: LoginUser?) {
                if (TextUtils.isEmpty(Objects.requireNonNull(loginUser)?.emailAddress)){

                }
            }

        })
    }

    private fun initUI() {
        /*pager=findViewById(R.id.pager)
        tabs=findViewById(R.id.tabs)

//        Adding fragments to tab
        tabs.setupWithViewPager(pager)
        tabs.addTab(tabs.newTab().setText(getString(R.string.login)))
        tabs.addTab(tabs.newTab().setText(getString(R.string.signup)))
        tabs.tabGravity= TabLayout.GRAVITY_FILL


//        setting ViewPager Adapter
//        setupViewPager(pager,tabs)

        val viewPagerAdapter = ViewPagerAdapter(this@MainActivity,supportFragmentManager,tabs.tabCount)
        pager.adapter=viewPagerAdapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.currentItem=tab!!.position
            }

        })*/

        pager=findViewById(R.id.pager)
        tabs=findViewById(R.id.tabs)

//        Adding fragments to tab
        tabs.addTab(tabs.newTab().setText(getString(R.string.login)))
        tabs.addTab(tabs.newTab().setText(getString(R.string.signup)))
        tabs.tabGravity= TabLayout.GRAVITY_FILL
//        tabs.setupWithViewPager(pager)

//        setting ViewPager Adapter
        val viewPagerAdapter = ViewPagerAdapter(this@MainActivity,supportFragmentManager,tabs.tabCount)
        pager.adapter=viewPagerAdapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.currentItem=tab!!.position
            }

        })

    }

    private inner class ViewPagerAdapter(var context: Context,var manager: FragmentManager, var tabCount: Int) : FragmentStatePagerAdapter(manager) {
        override fun getItem(position: Int): Fragment {
            return when(position){
                1->{
                    return RegisterFragment()
                }
                else -> {
                    return LoginFragment()
                }
            }
        }

        override fun getCount(): Int {
            return tabCount
        }

    }

}

