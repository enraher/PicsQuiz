package com.pics.quiz.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameData(
    val packageId: String,
    val packageVersion: Int,
    val numberLevels: Int,
    val levelId: String,
    val levelVersion: Int,
    val firstSubLevel: Int,
    val modeSelected: Int,
    val names: ArrayList<String>
) : Parcelable