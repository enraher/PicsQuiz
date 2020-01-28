package com.pics.quiz.other

import android.content.Context
import android.content.Intent
import com.pics.quiz.modules.splash.SplashActivity

object Navigator {
    fun startMenuActivity(context: Context) {
//        val intent = MenuActivity.createIntent(context)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//        context.startActivity(intent)
    }

    fun startSplashActivity(context: Context) {
        val intent = SplashActivity.createIntent(context)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
