package com.pics.quiz.model.database

import androidx.room.Embedded
import androidx.room.Relation

data class PackageWithLevelPlayed (
    @Embedded
    val packagePlayed: PackagePlayed,
    @Relation(
        parentColumn = LevelPlayed.LEVEL_ID,
        entityColumn = LevelPlayed.PACKAGE_ID,
        entity = LevelPlayed::class
    ) val levelsPlayed: List<LevelPlayed>
)

