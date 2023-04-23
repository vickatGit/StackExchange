package com.example.mathongo.domain.repository

import com.example.mathongo.data.model.QuestionListModel.QuestionListModel

interface Repo {
    suspend fun getQuestions(pageNumber: Int): QuestionListModel

    suspend fun getQuestionByQuery(pageNumber: Int, questionQuery: String, tag: String):QuestionListModel
}