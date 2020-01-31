package com.pics.quiz.other

import android.content.Context
import com.google.gson.Gson
import com.pics.quiz.model.AppContent
import com.pics.quiz.repositories.FileAccessRepository
import java.io.File

class MainController private constructor(val context: Context) {

    private var appContent: AppContent? = null

    fun setAppContent(file: File){
        appContent = Gson().fromJson(FileAccessRepository.readStringFromFile(file), AppContent::class.java)
        appContent?.orderPackages()
    }

    fun getAppContent(): AppContent? {
        appContent?.let {
            return it
        } ?: run {
            Navigator.startSplashActivity(context)
        }
        return null
    }

    companion object : SingletonHolder<MainController, Context>(::MainController)
}