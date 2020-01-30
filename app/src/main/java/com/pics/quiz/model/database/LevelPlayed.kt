package com.pics.quiz.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.pics.quiz.model.GameData
import com.pics.quiz.model.database.LevelPlayed.Companion.TABLE_NAME
import com.pics.quiz.other.Constants
import com.pics.quiz.other.Constants.Companion.SCORE_FACTOR
import com.pics.quiz.repositories.database.Converters
import java.io.Serializable


@Entity(tableName = TABLE_NAME)
data class LevelPlayed(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = LEVEL_ID)
    val id: String,

    @ColumnInfo(name = PACKAGE_ID)
    val packageId: String,

    @ColumnInfo(name = COMPLETED_NORMAL)
    var completedNormal: Int = 0,

    @ColumnInfo(name = COMPLETED_TIMED)
    var completedTimed: Int = 0,

    @ColumnInfo(name = WRONG_TIMED)
    var wrongTimed: Int = 0,

    @ColumnInfo(name = SCORE_TIMED)
    var scoreTimed: Int = 0,

    @ColumnInfo(name = SCORE_NORMAL)
    var scoreNormal: Int = 0,

    @ColumnInfo(name = CLEARED_COVER_NORMAL)
    @TypeConverters(Converters::class)
    var clearedCoverNormal: List<Int>,

    @ColumnInfo(name = CLEARED_COVER_TIMED)
    @TypeConverters(Converters::class)
    var clearedCoverTimed: List<Int>,

    @ColumnInfo(name = IS_OPEN)
    var isOpen: Boolean = false
) {

    companion object {
        const val TABLE_NAME = "level_played"
        const val LEVEL_ID = "id"
        const val PACKAGE_ID = "package_id"
        const val COMPLETED_NORMAL = "completed_normal"
        const val COMPLETED_TIMED = "completed_timed"
        const val WRONG_TIMED = "wrong_timed"
        const val SCORE_TIMED = "score_timed"
        const val SCORE_NORMAL = "score_normal"
        const val CLEARED_COVER_NORMAL = "clearedCoverNormal"
        const val CLEARED_COVER_TIMED = "clearedCoverTimed"
        const val IS_OPEN = "isOpen"
    }

    fun getClearedCovers(mode: Int): List<Int> {
        return if(mode == Constants.Companion.Mode.NORMAL.value) {
            clearedCoverNormal
        } else {
            clearedCoverTimed
        }
    }

    fun setClearedCovers(clearedCovers: List<Int>, mode: Int) {
        if(mode == Constants.Companion.Mode.NORMAL.value) {
            clearedCoverNormal = clearedCovers
        } else {
            clearedCoverTimed = clearedCovers
        }
    }

    fun getNormalCompleted(numberLevels: Int): String {
        return "$completedNormal/$numberLevels"
    }
    fun getTimedCompleted(numberLevels: Int): String {
        return "$completedTimed/$numberLevels"
    }

    fun getScoreForMode(mode: Int): Int {
        return if(mode == Constants.Companion.Mode.NORMAL.value) {
            scoreNormal
        } else {
            scoreTimed
        }
    }

    fun addScorePlusFactor(score: Int, mode: Int) {
        if(mode == Constants.Companion.Mode.NORMAL.value) {
            scoreNormal += (score * SCORE_FACTOR)
        } else {
            scoreTimed += (score * SCORE_FACTOR)
        }
    }




//    fun getWrongTimedPercentage(context: Context, numberLevels: Int): SpannableString {
//        val textWrong = ((wrongTimed * 100) / numberLevels).toString() + "% " + context.getString(R.string.wrong)
//        val textCompleted = getTimedCompleted(numberLevels)
//        val wordToSpan = SpannableString("$textCompleted\n$textWrong")
//
//        wordToSpan.setSpan(
//            ForegroundColorSpan(context.resources.getColor(R.color.txt_wrong)),
//            textCompleted.length,
//            wordToSpan.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        return wordToSpan
//    }


    fun getActualSublevel(gameData: GameData): Int {
        return if(gameData.modeSelected == Constants.Companion.Mode.NORMAL.value) {
            completedNormal + gameData.firstSubLevel
        } else {
            completedTimed + gameData.firstSubLevel
        }
    }

    fun getSubLevelsCompleted(gameData: GameData): Int {
        return if(gameData.modeSelected == Constants.Companion.Mode.NORMAL.value) {
            completedNormal
        } else {
            completedTimed
        }
    }

    fun reset(mode: Int) {
        if(mode == Constants.Companion.Mode.NORMAL.value) {
            clearedCoverNormal = ArrayList(0)
        } else {
            clearedCoverTimed = ArrayList(0)
        }

        if(mode == Constants.Companion.Mode.NORMAL.value) {
            scoreNormal = 0
            completedNormal = 0
        } else {
            scoreTimed = 0
            completedTimed = 0
            wrongTimed = 0
        }
    }

//    fun setActualSublevel(id: Int, mode: Int) {
//        if(mode == ConstantsEnrasoft.MODE_SELECTED_NORMAL) {
//            completed_normal = id
//        } else {
//            completed_timed = id
//        }
//    }

    fun setNextSubLevel(mode: Int) {
        if(mode == Constants.Companion.Mode.NORMAL.value) {
            completedNormal++
        } else {
            completedTimed++
        }
    }

    fun setWrongSubLevel() {
        wrongTimed++
    }
}