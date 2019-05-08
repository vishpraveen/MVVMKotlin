package com.example.mvvmkotlindemo.views


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkotlindemo.R
import com.example.mvvmkotlindemo.databinding.AdapterRepoListBinding
import com.example.mvvmkotlindemo.model.Item
import com.example.mvvmkotlindemo.viewmodel.RepoListViewModel
import com.example.mvvmkotlindemo.viewmodel.interfaces.EnumClick
import com.example.mvvmkotlindemo.viewmodel.interfaces.RVClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_repo_list.view.*

class RepoListAdapter(
    private val repoListViewModel: RepoListViewModel
) : RecyclerView.Adapter<RepoListAdapter.RepoListViewHolder>() {
    var repoList: List<Item> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = AdapterRepoListBinding.inflate(inflater, parent, false)
        return RepoListViewHolder(dataBinding, repoListViewModel)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        holder.setup(repoList[position])
    }

    fun updateRepoList(repoList: List<Item>) {
        this.repoList = repoList
        notifyDataSetChanged()
    }

    class RepoListViewHolder constructor(
        private val dataBinding: ViewDataBinding,
        private val repoListViewModel: RepoListViewModel
    )
        : RecyclerView.ViewHolder(dataBinding.root) {

        val avatarImage = itemView.avatarImage

        fun setup(itemData: Item) {
            dataBinding.setVariable(BR.itemData, itemData)
            dataBinding.executePendingBindings()

            Picasso.get().load(itemData.owner.avatar_url).into(avatarImage)

            itemView.setOnClickListener {
                val bundle= bundleOf("url" to itemData.html_url)
                Navigation.findNavController(it).navigate(R.id.action_repoListFragment_to_repoDetailFragment, bundle)
            }
        }
    }
}