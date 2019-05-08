package com.example.mvvmkotlindemo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mvvmkotlindemo.model.Item

class RepoListViewModel : BaseViewModel() {
    private var TAG: String=RepoListViewModel::class.java.simpleName
    open val repoListLive= MutableLiveData<List<Item>>()
    fun fetchRepoList(){
        dataLoading.value=true
        RepoRepository.getInstance().getRepoList { isSuccess, response ->
            dataLoading.value=false
            if (isSuccess){
                repoListLive.value=response?.items
                Log.e(TAG,"RepoListViewModel: "+response?.items?.size)
                empty.value=false
            }
            else{
                empty.value=true
            }
        }

    }
}