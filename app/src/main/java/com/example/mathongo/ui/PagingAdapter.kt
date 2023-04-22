package com.example.mathongo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mathongo.R
import com.example.mathongo.data.model.QuestionListModel.Question

class PagingAdapter : PagingDataAdapter<Question, PagingAdapter.QuestionHolder>(
    COMPARATOR) {

    class QuestionHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val question = itemView.findViewById<TextView>(R.id.question_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_holder, parent, false)
        return QuestionHolder(view)
    }


    override fun onBindViewHolder(holder: QuestionHolder, position: Int) {
        val item = getItem(position)
        holder.question.text = item?.title
    }


    companion object{
        private val COMPARATOR = object: DiffUtil.ItemCallback<Question>(){
            override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
                return oldItem.questionId == newItem.questionId
            }

            override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
                return oldItem == newItem
            }
        }
    }
}