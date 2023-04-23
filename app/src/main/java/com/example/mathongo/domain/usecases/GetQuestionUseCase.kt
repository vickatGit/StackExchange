package com.example.mathongo.domain.usecases

import com.example.mathongo.data.model.QuestionListModel.QuestionListModel
import com.example.mathongo.domain.repository.Repo
import javax.inject.Inject

class GetQuestionUseCase (val repo:Repo) {
    suspend operator fun invoke(pageNumber:Int): QuestionListModel {
        return repo.getQuestions(pageNumber)
    }
}