package com.example.mathongo.data.room



import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mathongo.data.model.QuestionListModel.Question
import com.example.mathongo.data.model.TypeConverters.TagsConverter


@Database(entities = [Question::class], version = 9)
@TypeConverters(TagsConverter::class)
abstract class StackDatabase : RoomDatabase() {
    abstract val dbDao : QuestionsDao
}