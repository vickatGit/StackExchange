package com.example.mathongo.domain.usecases

import com.example.mathongo.data.model.QuestionListModel.QuestionListModel
import com.example.mathongo.domain.repository.Repo

class GetQuestionByQueryUseCase (val repo: Repo) {
    suspend operator fun invoke( pageNumber :Int, query:String,tag:String): QuestionListModel {
        return repo.getQuestionByQuery(pageNumber,query,tag)
    }
}