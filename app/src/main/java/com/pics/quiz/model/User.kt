package com.pics.quiz.model

import android.content.Context
import com.pics.quiz.other.Constants
import com.pics.quiz.repositories.PreferencesManager
import java.io.Serializable

data class User(
    var stars: Int,
//    var score: Int = 0,
    var difficultySelected: Int = Constants.Companion.Difficulty.NORMAL.value
) : Serializable {

    fun stringCoins() : String {
        return stars.toString()
    }

//    companion object {
//        fun addScore(context: Context, scor: Int) {
//            val user = PreferencesManager(context).user
//            user?.score?.plus(scor)
//            PreferencesManager(context).user = user
//        }
//    }
}