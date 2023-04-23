package com.example.mathongo.data.model.TypeConverters

import androidx.room.TypeConverter


class TagsConverter{
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return value?.split(",") ?: emptyList()
    }

    @TypeConverter
    fun toString(list: List<String?>?): String? {
        return list?.joinToString(",")
    }
}