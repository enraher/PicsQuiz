package com.pics.quiz.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pics.quiz.model.database.PackagePlayed.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class PackagePlayed(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID)
    val id: String
) {
    companion object {
        const val TABLE_NAME = "package_played"
        const val ID = "id"
    }
}