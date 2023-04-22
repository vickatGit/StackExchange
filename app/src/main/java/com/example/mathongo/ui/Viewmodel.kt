package com.example.mathongo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.mathongo.data.model.QuestionListModel.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class Viewmodel @Inject constructor(pager : LiveData<PagingData<Question>> )  : ViewModel() {
    val flow = pager
}