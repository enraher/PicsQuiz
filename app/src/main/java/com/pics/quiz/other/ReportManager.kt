package com.pics.quiz.other

import android.content.Context
import android.os.Bundle
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics

class ReportManager private constructor(val context: Context) {

    fun report(name: String, action: String? = null, label: String? = null, map: HashMap<String, String>? = null) {
        val bundle = Bundle()
        bundle.putString("action", action)
        bundle.putString("label", label)
        if (map != null) {
            for ((key, value) in map) {
                bundle.putString(key, value)
            }
        }
        FirebaseAnalytics.getInstance(context).logEvent(name, bundle)
    }

    fun crashReport(message: String?) {
        try {
            Crashlytics.log(1, "ERROR", message)
        } catch (e: IllegalStateException) {
        }
    }

    fun crashReport(ex: Exception) {
        crashReport(ex.message)
    }

    companion object : SingletonHolder<ReportManager, Context>(::ReportManager)
}