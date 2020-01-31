package com.pics.quiz.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pics.quiz.model.database.PackagePlayed.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class PackagePlayed(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID)
    val id: String,

    @ColumnInfo(name = TOTAL_LEVELS_COMPLETED)
    var totalLevelsCompleted: Int,

    @ColumnInfo(name = TOTAL_SCORE)
    var totalScore: Int = 0
) {
    fun addScore(scor: Int) {
        totalScore += scor
    }

    fun getProgress(numberLevels: Int): Int {
        return (totalLevelsCompleted * 100) / (numberLevels * 2) // Timed + normal
    }

    companion object {
        const val TABLE_NAME = "package_played"
        const val ID = "id"
        const val TOTAL_LEVELS_COMPLETED = "total_levels_completed"
        const val TOTAL_SCORE = "total_score"
    }
}