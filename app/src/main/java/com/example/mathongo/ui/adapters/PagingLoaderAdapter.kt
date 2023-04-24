package com.example.mathongo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mathongo.R

class PagingLoaderAdapter : LoadStateAdapter<PagingLoaderAdapter.LoaderHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.paging_loader, parent, false)
        return LoaderHolder(view)
    }

    override fun onBindViewHolder(holder: LoaderHolder, loadState: LoadState) {
        holder.isLoaderShouldBeVisible(loadState = loadState)
    }

    class LoaderHolder(itemView: View) : ViewHolder(itemView) {
        val loader: ProgressBar = itemView.findViewById(R.id.loader)
        fun isLoaderShouldBeVisible(loadState: LoadState){
            loader.isVisible=loadState is LoadState.Loading
        }

    }
}