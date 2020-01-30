package com.pics.quiz.repositories

import androidx.room.*
import com.pics.quiz.model.database.LevelPlayed

@Dao
interface LevelPlayedDAO {

    @Query("SELECT * FROM ${LevelPlayed.TABLE_NAME}")
    fun getLevelsPlayed(): Array<LevelPlayed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg note: LevelPlayed): LongArray

    @Update
    fun updateAll(vararg note: LevelPlayed): Int

    @Delete
    fun delete(note: LevelPlayed): Int
}