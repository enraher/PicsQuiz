package com.pics.quiz.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Level(
    val id: String,
    val version: Int,
    @SerializedName("number_levels") val levelsCount: Int,
    @SerializedName("first_sub_level") val firstSubLevel: Int,
    val free: Boolean,
    @SerializedName("not_all_languages") val notAllLanguages: Boolean
) : Serializable