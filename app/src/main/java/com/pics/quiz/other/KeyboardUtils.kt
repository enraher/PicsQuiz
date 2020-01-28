package com.pics.quiz.other

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by enrasoft on 24/1/20.
 */

@Suppress("unused")
object KeyboardUtils {

    fun showKeyboard(activity: Activity, view: View?) {
        view?.requestFocus()
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun hideKeyboard(activity: Activity?) {
        activity?.let {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            activity.currentFocus?.let { view ->
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                view.clearFocus()
            }
        }
    }

    fun hideKeyboard(activity: Activity?, view: View?) {
        activity?.let {
            view?.let {
                val inputMethodManager =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            } ?: run {
                hideKeyboard(activity)
            }
        }
    }
}
