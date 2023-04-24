package com.example.mathongo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mathongo.R
import com.example.mathongo.ui.adapters.PagingAdapter
import com.example.mathongo.ui.adapters.PagingLoaderAdapter
import com.example.mathongo.ui.Callbacks.QuestionClick
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: Viewmodel

    private lateinit var questionRecycler: RecyclerView

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var search: Button
    private lateinit var questionSearch:EditText
    private lateinit var tagsSearch:EditText
    private lateinit var toolbar: Toolbar
    private lateinit var inputContainer:LinearLayout
    private lateinit var searchIcon:ImageView
    private lateinit var progress:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)
        setContentView(R.layout.activity_main)
        initialiseViews()
        toolbar.title=""
        setSupportActionBar(toolbar)

        viewmodel = ViewModelProvider(this).get(Viewmodel::class.java)
        val adapter = PagingAdapter(object : QuestionClick{
            override fun onClick(url: String) {
                val intent=Intent(this@MainActivity,WebQuestionActivity::class.java)
                intent.putExtra(WebQuestionActivity.URL_TAG,url)
                startActivity(intent)
            }

        })
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (positionStart == 0) {
                    swipeRefresh.isRefreshing = false
                    questionRecycler.smoothScrollToPosition(0)
                }

            }

        })
        questionRecycler.setHasFixedSize(true)
        questionRecycler.layoutManager = LinearLayoutManager(this)
        questionRecycler.adapter = adapter.withLoadStateFooter( footer = PagingLoaderAdapter() )

        viewmodel.flow.observe(this) {
            adapter.submitData(lifecycle, it)
        }
        searchIcon.setOnClickListener {
            Log.e("TAG", "onCreate: search icon clicked", )
            if(inputContainer.isVisible){
                hideInputContainer(false)
            }else{
                showInputContainer()
            }
        }

        swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }
        search.setOnClickListener {
            if(!questionSearch.text.isEmpty()) {
                viewmodel.getQuestionsByQuery(
                    questionSearch.text.toString(),
                    tagsSearch.text.toString()
                ).observe(this) {
                    Log.e("TAG", "onCreate: in search",)
                    adapter.submitData(lifecycle, it)
                    Log.e("TAG", "onCreate: can observe")
                    swipeRefresh.isRefreshing = false
                }
                hideInputContainer(true)
            }

        }
    }

    private fun hideInputContainer(isQueryEntered: Boolean) {
        val slideUp=AnimationUtils.loadAnimation(this@MainActivity,R.anim.slide_up)
        inputContainer.startAnimation(slideUp)
        slideUp.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                inputContainer.isVisible=false
                if(isQueryEntered)
                    questionRecycler.smoothScrollToPosition(0)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    private fun showInputContainer() {
        val slideDown=AnimationUtils.loadAnimation(this@MainActivity,R.anim.slide_down)
        inputContainer.startAnimation(slideDown)
        slideDown.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                inputContainer.isVisible=true

            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })


    }

    private fun initialiseViews() {
        questionRecycler = findViewById(R.id.question_recycler)
        swipeRefresh = findViewById(R.id.refresh)
        search = findViewById(R.id.query)
        questionSearch=findViewById(R.id.question)
        tagsSearch=findViewById(R.id.tags)
        toolbar=findViewById(R.id.toolbar)
        inputContainer=findViewById(R.id.input_container)
        searchIcon=toolbar.findViewById(R.id.search_icon)

        progress=findViewById(R.id.progress)

    }
}