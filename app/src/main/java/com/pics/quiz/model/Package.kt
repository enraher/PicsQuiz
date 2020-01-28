package com.pics.quiz.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Package (
    val id: String,
    val version: Int,
    val order: Int,
    val free: Boolean,
    @SerializedName("level_images") val levels: ArrayList<Level>,
    val name:  Map<String, String>,
    val available: Boolean,
    @SerializedName("new_levels_mark")  val isNewLevel: Boolean
) : Serializable {

    fun getLevelById(idLevel: String): Level? {
        for (level in levels) {
            if(idLevel == level.id){
                return level
            }
        }
        return null
    }

    fun countNumberLevels() : Int {
        var count = 0
        levels.forEach { levelGameData ->
            count += levelGameData.levelsCount
        }
        return count
    }

    fun getPackageInternalDir(): String {
        return "${id}_v$version"
    }
}