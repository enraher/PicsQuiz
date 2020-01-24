package com.pics.quiz.base

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by enraher on 23/01/20.
 */

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val context: Context
        get() = getApplication()

    open fun initialData(data: Bundle) { }

    fun backgroundProcess(backgroundProcess: () -> Any?,
                                  foregroundResponse: ((Any?) -> Unit)? = null) {
        viewModelScope.launch {
            var response: Any? = null
            withContext(Dispatchers.Default) {
                response = backgroundProcess()
            }

            foregroundResponse?.let {
                withContext(Dispatchers.Main) {
                    foregroundResponse(response)
                }
            }
        }
    }

    fun dataBackgroundProcess(dataBackgroundProcess: () -> Any?,
                                      foregroundResponse: ((Any?) -> Unit)? = null) {
        viewModelScope.launch {
            var response: Any? = null
            withContext(Dispatchers.IO) {
                response = dataBackgroundProcess()
            }

            foregroundResponse?.let {
                withContext(Dispatchers.Main) {
                    foregroundResponse(response)
                }
            }
        }
    }
}