package com.example.mathongo.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mathongo.R
import com.example.mathongo.data.model.QuestionListModel.Question
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.gson.Gson


class PagingAdapter : PagingDataAdapter<Question, PagingAdapter.QuestionHolder>(
    COMPARATOR
) {


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Question>() {
            override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
                return oldItem.questionId == newItem.questionId
            }

            override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.question_holder, parent, false)
        return QuestionHolder(view)
    }


    override fun onBindViewHolder(holder: QuestionHolder, position: Int) {
        val question = getItem(position)
        Log.e("TAG", "onBindViewHolder: ${Gson().toJson(question)}")
        holder.question.text = question?.title
        holder.userName.text = question?.owner?.displayName
        holder.userProfilePic.load(question?.owner?.profileImage) {
            placeholder(R.drawable.user_default_avatar)
        }
        question?.tags?.let {
            val layoutManager = FlexboxLayoutManager(holder.tagsRecycler.context)
            layoutManager.setFlexDirection(FlexDirection.ROW)
            layoutManager.setJustifyContent(JustifyContent.FLEX_START)
            holder.tagsRecycler.setLayoutManager(layoutManager)
            val tagsAdapter = TagsAdapter(it)
            holder.tagsRecycler.adapter = tagsAdapter
        }
        holder.viewsCount.text = question?.viewCount.toString()
        holder.votes.text = question?.score.toString()
        holder.answerCount.text = question?.answerCount.toString()


    }


    class QuestionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question: TextView = itemView.findViewById(R.id.question_title)
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val userProfilePic: ImageView = itemView.findViewById(R.id.user_profile_image)
        val tagsRecycler: RecyclerView = itemView.findViewById(R.id.tags_recycler)
        val postDuration: TextView = itemView.findViewById(R.id.timestamp)
        val votes: TextView = itemView.findViewById(R.id.votes)
        val answerCount: TextView = itemView.findViewById(R.id.answer_count)
        val viewsCount: TextView = itemView.findViewById(R.id.views)

    }
}