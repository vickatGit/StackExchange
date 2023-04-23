package com.example.mathongo.data.remote

import android.content.SharedPreferences
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mathongo.data.model.QuestionListModel.Question
import com.example.mathongo.data.model.QuestionListModel.QuestionListModel
import com.example.mathongo.data.room.StackDatabase
import com.example.mathongo.domain.usecases.GetQuestionByQueryUseCase
import com.example.mathongo.domain.usecases.GetQuestionUseCase
import com.google.gson.Gson
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@ExperimentalPagingApi
class QuestionsRemoteMediator @Inject constructor(
    private val db: StackDatabase,
    private val getQuestionUseCase: GetQuestionUseCase,
    private val sharedPreferences: SharedPreferences,
    private val getQuestionByQueryUseCase: GetQuestionByQueryUseCase,
    private val questionQuery: String = "",
    private val tag: String = ""
) : RemoteMediator<Int, Question>() {

    private val PAGE_NO: String = "last_page_no"
    private lateinit var questions:QuestionListModel

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Question>
    ): MediatorResult {
        Log.e("TAG", "load: ")
        return try {
            val pageNumber = when (loadType) {
                LoadType.REFRESH -> {
                    sharedPreferences.edit().clear().apply()
                    1
                }
                LoadType.PREPEND -> {
                    MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    Log.e("TAG", "load: append")
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
            Log.e("TAG", "load: query $questionQuery pagenumber $pageNumber")

            if (pageNumber is Int) {
                if (!questionQuery.isEmpty() || !tag.isEmpty()) {


                    questions = getQuestionByQueryUseCase(
                        pageNumber = pageNumber,
                        query = questionQuery,
                        tag = tag
                    )

                } else {

                    questions = getQuestionUseCase.invoke(pageNumber.toString().toInt())


//            Log.e("TAG", "load: ${Gson().toJson(questions)}" )

                }
                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.dbDao.clearAll()
                    }
                    db.dbDao.upsertAll(question = questions.items)
                }
            }


            MediatorResult.Success(
                endOfPaginationReached = false
            )


        } catch (e: IOException) {
            Log.e("TAG", "load io: ${e.localizedMessage}")
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e("TAG", "load http: ${e.localizedMessage}")
            MediatorResult.Error(e)
        }
    }
}