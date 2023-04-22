package com.example.mathongo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.mathongo.data.model.QuestionListModel.Question
import com.example.mathongo.data.model.QuestionListModel.QuestionListModel
import com.example.mathongo.data.remote.ApiInterface
import com.example.mathongo.data.remote.QuestionsRemoteMediator
import com.example.mathongo.domain.repository.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoImpl(val api: ApiInterface) : Repo {
    private val questionLiveData= MutableLiveData<List<Question?>?>()
    fun  getQuestionsData():LiveData<List<Question?>?> =  questionLiveData




    override suspend fun getQuestions(){

//        api.getQuestions(0, 10).enqueue(object : Callback<QuestionListModel> {
//            override fun onResponse(
//                call: Call<QuestionListModel>,
//                response: Response<QuestionListModel>
//            ) {
//               response.body()?.let {
//                   questionLiveData.postValue(it.items)
//               }
//            }
//
//            override fun onFailure(call: Call<QuestionListModel>, t: Throwable) {
//               questionLiveData.postValue(null)
//            }
//
//        })
    }
}