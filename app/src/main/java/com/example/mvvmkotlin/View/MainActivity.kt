package com.example.mvvmkotlin.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.mvvmkotlin.Model.LoginUser
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.ViewModel.LoginViewModel
import com.google.android.material.tabs.TabLayout
import java.util.*

class MainActivity : AppCompatActivity() {

    private var loginViewModel : LoginViewModel? =null

    private lateinit var pager : ViewPager
    private lateinit var tabs : TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        pager=findViewById(R.id.pager)

        tabs=findViewById(R.id.tabs)
        tabs.addTab(tabs.newTab().setText(getString(R.string.login)),true)
        tabs.addTab(tabs.newTab().setText(getString(R.string.signup)))
        tabs.setupWithViewPager(pager)

        setupViewPager(pager,tabs)


    }

    private fun setupViewPager(
        pager: ViewPager,
        tabs: TabLayout
    ) {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,tabs.tabCount)
        pager.adapter=viewPagerAdapter
    }

    private inner class ViewPagerAdapter(manager: FragmentManager, tabCount: Int) : FragmentPagerAdapter(manager) {
        override fun getItem(position: Int): Fragment {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}

