package com.example.mathongo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mathongo.R

class TagsAdapter(val tags: List<String?>): RecyclerView.Adapter<TagsAdapter.TagHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.tag_holder,parent,false)
        return TagHolder(view)
    }

    override fun onBindViewHolder(holder: TagHolder, position: Int) {
        holder.tag.text= tags[holder.bindingAdapterPosition]
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    class TagHolder(itemView: View) : ViewHolder(itemView){
        val tag : TextView=itemView.findViewById(R.id.tag)
    }
}