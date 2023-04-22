package com.example.mathongo.data.remote


import com.example.mathongo.data.model.QuestionListModel.QuestionListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("questions")
    suspend fun getQuestions(
        @Query("page") pageNum: Int,
        @Query("pagesize") pageSize :Int = 10 ,
        @Query("order") order : String = "desc",
        @Query("site") site : String = "stackoverflow"
    ): QuestionListModel
}