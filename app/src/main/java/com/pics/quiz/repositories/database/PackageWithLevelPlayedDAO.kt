package com.pics.quiz.repositories.database

import androidx.room.*
import com.pics.quiz.model.database.PackagePlayed
import com.pics.quiz.model.database.PackageWithLevelsPlayed


@Dao
interface PackageWithLevelPlayedDAO {
    @Transaction
    @Query("SELECT * FROM ${PackagePlayed.TABLE_NAME} WHERE ${PackagePlayed.ID} = :packageId")
    fun getPackageWithLevelsPlayed(packageId: String): PackageWithLevelsPlayed

    @Transaction
    @Query("SELECT * FROM ${PackagePlayed.TABLE_NAME}")
    fun getPackagesWithLevelsPlayed(): List<PackageWithLevelsPlayed>?

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(vararg note: PackagePlayed): LongArray
//
//    @Update
//    fun updateAll(vararg note: PackagePlayed): Int
//
//    @Delete
//    fun delete(note: PackagePlayed): Int
}