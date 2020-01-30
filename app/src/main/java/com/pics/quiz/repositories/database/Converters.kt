package com.pics.quiz.repositories.database


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long) = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date) = date.time

    @TypeConverter
    fun getListFromString(genreIds: String): List<Int> {
        val list = mutableListOf<Int>()

        val array = genreIds.split(",".toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()

        for (s in array) {
            if (s.isNotEmpty()) {
                list.add(s.toInt())
            }
        }
        return list
    }

    @TypeConverter
    fun setStringFromList(list: List<Int>): String {
        var genreIds=""
        for (i in list) genreIds += ",$i"
        return genreIds
    }
}