package com.example.mathongo.ui

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.mathongo.data.model.QuestionListModel.Question
import com.example.mathongo.data.remote.QuestionsRemoteMediator
import com.example.mathongo.data.room.StackDatabase
import com.example.mathongo.domain.usecases.GetQuestionByQueryUseCase
import com.example.mathongo.domain.usecases.GetQuestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class Viewmodel @Inject constructor(
    val db: StackDatabase,
    val preferences: SharedPreferences,
    val getQuestionUseCase: GetQuestionUseCase,
    val getQuestionByQueryUseCase: GetQuestionByQueryUseCase,
    var pager: LiveData<PagingData<Question>>
) : ViewModel() {

    var flow = pager

    fun getQuestionsByQuery(query: String, tag: String): LiveData<PagingData<Question>> {
        return Pager(
            PagingConfig(pageSize = 10),
            remoteMediator =  QuestionsRemoteMediator(
                db,
                getQuestionUseCase,
                preferences,
                getQuestionByQueryUseCase,
                query,
                tag
            ),
            pagingSourceFactory = {
                db.dbDao.pagingSource()
            }
        ).liveData


    }
}