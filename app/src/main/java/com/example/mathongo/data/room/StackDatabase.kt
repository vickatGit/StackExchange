package com.example.mathongo.data.room



import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mathongo.data.model.QuestionListModel.Question


@Database(entities = [Question::class], version = 3)
abstract class StackDatabase : RoomDatabase() {
    abstract val dbDao : QuestionsDao
}