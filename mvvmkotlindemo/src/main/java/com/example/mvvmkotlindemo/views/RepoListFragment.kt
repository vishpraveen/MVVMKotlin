package com.example.mvvmkotlindemo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmkotlindemo.databinding.FragmentRepoListBinding
import com.example.mvvmkotlindemo.viewmodel.RepoListViewModel
import com.example.mvvmkotlindemo.viewmodel.interfaces.EnumClick
import com.example.mvvmkotlindemo.viewmodel.interfaces.RVClickListener
import kotlinx.android.synthetic.main.fragment_repo_list.*

class RepoListFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentRepoListBinding
    private lateinit var adapter : RepoListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding= FragmentRepoListBinding.inflate(inflater,container,false).apply {
            viewmodel= ViewModelProviders.of(this@RepoListFragment).get(RepoListViewModel::class.java)
            setLifecycleOwner(viewLifecycleOwner)
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewmodel?.fetchRepoList()

        setAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        viewDataBinding.viewmodel?.repoListLive?.observe(viewLifecycleOwner, Observer {
            adapter.updateRepoList(it)
        })

        viewDataBinding.viewmodel?.toastMessage?.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel!=null){
            adapter = RepoListAdapter(viewDataBinding.viewmodel!!)
            val layoutManager = LinearLayoutManager(activity)
            repo_list_rv.layoutManager = layoutManager
            repo_list_rv.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
            repo_list_rv.adapter = adapter
        }
    }

}