package com.example.mathongo.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.paging.*
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mathongo.data.model.QuestionListModel.Question
import com.example.mathongo.data.remote.ApiInterface
import com.example.mathongo.data.remote.QuestionsRemoteMediator
import com.example.mathongo.data.repository.RepoImpl
import com.example.mathongo.data.room.StackDatabase
import com.example.mathongo.domain.repository.Repo
import com.example.mathongo.domain.usecases.GetQuestionByQueryUseCase
import com.example.mathongo.domain.usecases.GetQuestionUseCase
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCache(context: Application): Cache {
        val cacheDirectory = File(context.getCacheDir(), "responses")
        val cacheSize = 10 * 1024 * 1024 //----- 10 MB------
        val cache = Cache(cacheDirectory, cacheSize.toLong())
        return cache
    }

    @Provides
    @Singleton
    fun provideHttpClient(@ApplicationContext app: Context,cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(app))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://api.stackexchange.com/2.3/")
            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
            .build()
            .create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideRepo(api: ApiInterface): Repo {
        return RepoImpl(api)
    }

    @Provides
    @Singleton
    fun provideStackDatabase(@ApplicationContext app: Context): StackDatabase {
        return Room.databaseBuilder(app, StackDatabase::class.java, "StackDatabase.db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideQuestionPager(
        db: StackDatabase,
        questionsRemoteMediator: QuestionsRemoteMediator
    ): LiveData<PagingData<Question>> {
        return Pager(
            PagingConfig(pageSize = 10),
            remoteMediator = questionsRemoteMediator,
            pagingSourceFactory = {
                db.dbDao.pagingSource()
            }
        ).liveData
    }

    @Provides
    @Singleton
    fun getQuestionsUseCase(repo:Repo): GetQuestionUseCase {
        return GetQuestionUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideQuestionByQueryUseCase(repo:Repo): GetQuestionByQueryUseCase {
        return GetQuestionByQueryUseCase(repo)
    }

    @Provides
    fun provideQuestionRemoteMediator(
        db: StackDatabase,
        preferences: SharedPreferences,
        getQuestionUseCase: GetQuestionUseCase,
        getQuestionByQueryUseCase: GetQuestionByQueryUseCase
    ): QuestionsRemoteMediator {
        return QuestionsRemoteMediator(db,getQuestionUseCase, preferences,getQuestionByQueryUseCase)
    }


}