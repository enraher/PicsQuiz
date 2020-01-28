package com.pics.quiz.modules.splash

import android.app.Application
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pics.quiz.base.BaseViewModel
import com.pics.quiz.model.Packages
import com.pics.quiz.model.Version
import com.pics.quiz.other.Constants
import com.pics.quiz.other.Constants.Companion.APP_VERSION
import com.pics.quiz.other.isInternetAvailable
import com.pics.quiz.repositories.FileAccessRepository
import com.pics.quiz.repositories.FileAccessRepository.checkIfImageFilesExist
import com.pics.quiz.repositories.FileAccessRepository.unzip
import com.pics.quiz.repositories.FirebaseRepository
import java.io.File

/**
 * Created by enrasoft on 24/1/20.
 */

class SplashViewModel(application: Application) : BaseViewModel(application) {


    private var loadReady: Boolean = false

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> get() = _progress

    private val _errorTag = MutableLiveData<String>()
    val errorTag: LiveData<String> get() = _errorTag

    private val _initGame = MutableLiveData<Boolean>()
    val initGame: LiveData<Boolean> get() = _initGame


    var mainActivityAccessible: Boolean = false


    fun getAppVersion() {
        _progress.value = 90
        dataBackgroundProcess({ isInternetAvailable() }) { isInternetAvailable ->
            if(isInternetAvailable is Boolean && isInternetAvailable) {
                FirebaseRepository.getVersions { version, error ->
                    version?.let {
                        if(APP_VERSION < version.appVersion) {
                            _errorTag.value = SplashActivity.TAG_UPDATE_APP_ERROR
                        } else {
                            getPackagesJsonVersion(version)
                        }
                    }
                    error?.let {
                        _errorTag.value = SplashActivity.TAG_GENERIC_ERROR
                    }
                }
            } else {
                _errorTag.value = SplashActivity.TAG_NO_INTERNET_ERROR
            }
        }
    }

    private fun getPackagesJsonVersion(version: Version) {
        _progress.value = 70
        val jsonName = "${Constants.PACKAGES_JSON_NAME_FILE}${version.packagesJsonVersion}.json"
        val file = FileAccessRepository.getFileFromMemory(context, jsonName)
        if(!file.exists()) {
            FirebaseRepository.getFileFromFirebase(context, "", jsonName) { fileGot ->
                fileGot?.let {
                    loadFileData(fileGot)
                } ?: run {
                    _errorTag.value = SplashActivity.TAG_GENERIC_ERROR
                    Log.e(TAG, "Error retrieving file")
                }
            }
        } else {
            loadFileData(file)
        }
    }

    private fun loadFileData(file: File){
        _progress.value = 50
        val packages = Gson().fromJson(FileAccessRepository.readStringFromFile(file), Packages::class.java)
        packages.orderPackages()
        if(checkIfImageFilesExist(context, packages.fileImages ?: "")) {
            //TODO load user data
        } else {
            _progress.value = 40
            val fileName = "${packages.fileImages}.zip"
            FirebaseRepository.getFileFromFirebase(context, "", fileName) { fileGot ->
                fileGot?.let {
                    _progress.value = 20
                    backgroundProcess({ unzip(context, it) }) {
                        //TODO load user data
                    }
                } ?: run {
                    _errorTag.value = SplashActivity.TAG_GENERIC_ERROR
                    Log.e(TAG, "Error retrieving file")
                }
            }
        }
    }

    companion object {
        private const val TAG = "SplashViewModel"
    }
}