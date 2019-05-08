package com.example.mvvmkotlin.views

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.getSystemService
import com.example.mvvmkotlin.R

class MoreActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)
        toolbar=findViewById(R.id.toolbar)
//        toolbar.inflateMenu(R.menu.more_toolbar_menu)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.more_toolbar_menu,menu)
        val searchItem: MenuItem= menu!!.findItem(R.id.search)
        val searchManager: SearchManager= this.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchView: SearchView?=null
        if (searchItem!=null){
            searchView=searchItem.actionView as SearchView
        }
        if (searchView!=null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this@MoreActivity.componentName))
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.search->{

            }
        }
        return true
    }
}
