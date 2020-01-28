package com.pics.quiz.other

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.pics.quiz.other.MyApplication.Companion.crashReport
import java.io.BufferedReader
import java.io.File
import java.net.InetAddress

/**
 * Created by enrasoft on 24/1/20.
 */

fun getResId(resName: String, context: Context): Int? {
    return try {
        context.resources.getIdentifier(resName, "drawable", context.packageName)
    } catch (e: Exception) {
        e.printStackTrace()
        crashReport(e)
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





