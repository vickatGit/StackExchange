
package com.example.mathongo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mathongo.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel : Viewmodel

    private lateinit var questionRecycler :RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseViews()
        viewmodel = ViewModelProvider(this).get(Viewmodel::class.java)
        val adapter = PagingAdapter()
        questionRecycler.setHasFixedSize(true)
        questionRecycler.layoutManager=LinearLayoutManager(this)
        questionRecycler.adapter = adapter

        viewmodel.flow.observe(this){
            adapter.submitData(lifecycle,it)
            Log.e("TAG", "onCreate: can observe", )
        }
    }

    private fun initialiseViews() {
        questionRecycler=findViewById(R.id.question_recycler)
    }
}