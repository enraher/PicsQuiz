package com.pics.quiz.other

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.pics.quiz.modules.menu.MenuActivity
import com.pics.quiz.modules.splash.SplashActivity

object Navigator {
    fun startMenuActivity(activity: Activity) {
        val intent = MenuActivity.createIntent(activity)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
        activity.overridePendingTransition(0, 0)
        activity.finish()
    }

    fun startSplashActivity(context: Context) {
        val intent = SplashActivity.createIntent(context)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
