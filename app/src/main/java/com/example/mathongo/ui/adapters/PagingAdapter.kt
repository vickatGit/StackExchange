package com.example.mathongo.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.mathongo.R
import com.example.mathongo.data.model.QuestionListModel.Question
import com.example.mathongo.ui.Callbacks.QuestionClick
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.admanager.AdManagerAdRequest


class PagingAdapter(val questionClick: QuestionClick) :
    PagingDataAdapter<Question, ViewHolder>(
        COMPARATOR
    ) {


    private val OFFSET:Int = 5


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0) {
            val view =LayoutInflater.from(parent.context).inflate(R.layout.question_holder, parent, false)
            QuestionHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.ad_holder, parent, false)
            AdHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()+super.getItemCount() + (super.getItemCount() / OFFSET)
    }

    override fun getItemViewType(position: Int): Int {

        return if (position > 0) {
            if ((position) % (OFFSET) == 0)  1  else 0
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("TAG", "getItemViewType: $position")
        val question = getItem(position)

            if (holder is QuestionHolder) {
                holder.question.text = question?.title
                holder.userName.text = question?.owner?.displayName
                holder.userProfilePic.load(question?.owner?.profileImage) {
                    placeholder(R.drawable.user_default_avatar)
                }
                question?.tags?.let {
                    val layoutManager = FlexboxLayoutManager(holder.tagsRecycler.context)
                    layoutManager.flexDirection = FlexDirection.ROW
                    layoutManager.justifyContent = JustifyContent.FLEX_START
                    holder.tagsRecycler.layoutManager = layoutManager
                    val tagsAdapter = TagsAdapter(it)
                    holder.tagsRecycler.adapter = tagsAdapter
                }
                holder.viewsCount.text = question?.viewCount.toString()
                holder.votes.text = question?.score.toString()
                holder.answerCount.text = question?.answerCount.toString()
                holder.itemView.setOnClickListener {
                    question?.link?.let {
                        questionClick.onClick(it)
                    }
                }
            }
         else {
            val adHolder = holder as AdHolder
            adHolder.loadAd()
        }


    }


    class QuestionHolder(itemView: View) : ViewHolder(itemView) {
        val question: TextView = itemView.findViewById(R.id.question_title)
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val userProfilePic: ImageView = itemView.findViewById(R.id.user_profile_image)
        val tagsRecycler: RecyclerView = itemView.findViewById(R.id.tags_recycler)
        val votes: TextView = itemView.findViewById(R.id.votes)
        val answerCount: TextView = itemView.findViewById(R.id.answer_count)
        val viewsCount: TextView = itemView.findViewById(R.id.views)

    }

    class AdHolder(itemView: View) : ViewHolder(itemView) {
        private var adLoader =
            AdLoader.Builder(itemView.context, "ca-app-pub-3940256099942544/2247696110").apply {
                forNativeAd {
                    val templateStyle = NativeTemplateStyle.Builder().build()
                    val template: TemplateView = itemView.findViewById(R.id.my_template)
                    template.setStyles(templateStyle)
                    template.setNativeAd(it)

                }
            }.build()

        fun loadAd() {
            adLoader.loadAd(AdManagerAdRequest.Builder().build())
        }

    }
}
