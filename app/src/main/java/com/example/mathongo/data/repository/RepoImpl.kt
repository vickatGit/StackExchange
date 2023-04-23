package com.example.mathongo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.mathongo.data.model.QuestionListModel.Question
import com.example.mathongo.data.model.QuestionListModel.QuestionListModel
import com.example.mathongo.data.remote.ApiInterface
import com.example.mathongo.data.remote.QuestionsRemoteMediator
import com.example.mathongo.domain.repository.Repo
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RepoImpl @Inject constructor(val api: ApiInterface) : Repo {


    override suspend fun getQuestions(pageNumber: Int): QuestionListModel {
         return api.getQuestions(pageNum = pageNumber)
    }

    override suspend fun getQuestionByQuery(
        pageNumber: Int,
        questionQuery: String,
        tag: String
    ): QuestionListModel {
        return api.getQuestionByQuery(
            pageNum = pageNumber,
            title = questionQuery,
            tagged = tag
        )
    }
}