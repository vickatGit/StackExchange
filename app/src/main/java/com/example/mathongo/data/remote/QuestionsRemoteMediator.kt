package com.example.mathongo.data.remote

import android.content.SharedPreferences
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mathongo.data.model.QuestionListModel.Question
import com.example.mathongo.data.room.StackDatabase
import com.google.gson.Gson
import okio.IOException
import retrofit2.HttpException

@ExperimentalPagingApi
class QuestionsRemoteMediator(
    private val db: StackDatabase,
    private val remote: ApiInterface,
    private val sharedPreferences: SharedPreferences
) : RemoteMediator<Int, Question>() {

    private val PAGE_NO: String = "last_page_no"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Question>
    ): MediatorResult {
        Log.e("TAG", "load: ", )
        return try {
            val pageNumber = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    Log.e("TAG", "load: append", )
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {

                        val pageNo: Int = sharedPreferences.getInt(PAGE_NO, 1)
                        sharedPreferences.edit().putInt(PAGE_NO, pageNo + 1).apply();
                        pageNo + 1
                    }
                }
            }

            if(pageNumber is Int) {
                val questions = remote.getQuestions(pageNumber.toString().toInt())



                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.dbDao.clearAll()
                    }
                    db.dbDao.upsertAll(question = questions.items)
                }

//            Log.e("TAG", "load: ${Gson().toJson(questions)}" )
            }
            MediatorResult.Success(
                endOfPaginationReached = false
            )


        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}