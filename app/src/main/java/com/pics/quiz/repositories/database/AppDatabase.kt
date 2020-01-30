package com.pics.quiz.repositories.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pics.quiz.model.database.LevelPlayed
import com.pics.quiz.model.database.PackagePlayed
import com.pics.quiz.repositories.LevelPlayedDAO

@Database(entities = [PackagePlayed::class, LevelPlayed::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun packagePlayedDao(): PackagePlayedDAO
    abstract fun packageWithLevelPlayedDao(): PackageWithLevelPlayedDAO
    abstract fun levelPlayedDao(): LevelPlayedDAO

    companion object {
        fun getInstance(context: Context) =
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    }
}
