package com.pics.quiz.other


/**
 * Created by enrasoft on 12/3/18.
 */
class Constants {
    companion object {

        const val PACKAGES_JSON_NAME_FILE = "packages_v"
        const val DISABLE_ANIMS = false
        const val APP_VERSION = 1
        const val SCORE_FACTOR = 123

        enum class Difficulty(val value: Int, millis: Long) {
            EASY(0, 20 * 1000L),
            NORMAL(1, 15 * 1000L),
            HARD(2, 10 * 1000L)
        }

        enum class Mode(val value: Int) {
            NORMAL(1),
            TIMED(0)
        }
    }

}

