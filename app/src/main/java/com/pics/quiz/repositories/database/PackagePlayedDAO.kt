package com.pics.quiz.repositories.database

import androidx.room.*
import com.pics.quiz.model.database.PackagePlayed

@Dao
interface PackagePlayedDAO {

    @Query("SELECT * FROM ${PackagePlayed.TABLE_NAME}")
    fun getPackagesPlayed(): Array<PackagePlayed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg note: PackagePlayed): LongArray

    @Update
    fun updateAll(vararg note: PackagePlayed): Int

    @Delete
    fun delete(note: PackagePlayed): Int
}