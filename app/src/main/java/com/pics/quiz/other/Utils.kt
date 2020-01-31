package com.pics.quiz.other

import android.content.Context
import com.pics.quiz.repositories.PreferencesManager
import java.net.InetAddress
import java.util.*

/**
 * Created by enrasoft on 24/1/20.
 */

fun getResId(resName: String, context: Context): Int? {
    return try {
        context.resources.getIdentifier(resName, "drawable", context.packageName)
    } catch (e: Exception) {
        e.printStackTrace()
        ReportManager.getInstance(context).crashReport(e)
        null
    }
}


fun isInternetAvailable(): Boolean {
    return try {
        val inetAddress = InetAddress.getByName("google.com")
        !inetAddress.equals("")
    } catch (e: Exception) {
        false
    }
}

fun getCurrentLanguage(context: Context?): String {
    return if(context != null && PreferencesManager(context).languageSelected != "") {
        PreferencesManager(context).languageSelected
    } else {
        Locale.getDefault().language
    }
}



