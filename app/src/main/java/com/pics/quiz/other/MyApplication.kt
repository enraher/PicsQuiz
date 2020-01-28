package com.pics.quiz.other

import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics


/**
 * Created by enrasoft on 24/1/20.
 */

class MyApplication : MultiDexApplication() {


    companion object {
        fun crashReport(message: String?) {
            try {
                Crashlytics.log(1, "ERROR", message)
            } catch (e: IllegalStateException) {
            }
        }

        fun crashReport(ex: Exception) {
            crashReport(ex.message)
        }
    }
}