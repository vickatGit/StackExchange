package com.example.mathongo.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.mathongo.data.model.QuestionListModel.Question


@Dao
interface QuestionsDao {
    @Upsert
    suspend fun upsertAll(question: List<Question>)

    @Query("SELECT * FROM Question")
    fun pagingSource():PagingSource<Int,Question>

    @Query("DELETE FROM Question")
    suspend  fun clearAll()
}