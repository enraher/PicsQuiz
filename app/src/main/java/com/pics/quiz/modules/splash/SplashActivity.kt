package com.pics.quiz.modules.splash

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.pics.flubber.Flubber
import com.pics.flubber.Flubber.Preset
import com.pics.flubber.Flubber.Curve
import com.pics.quiz.extensions.anim
import com.pics.quiz.R
import com.pics.quiz.base.BaseActivity
import com.pics.quiz.modules.common.GenericDialog
import com.pics.quiz.other.Navigator
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by enrasoft on 24/1/20.
 */

class SplashActivity : BaseActivity<SplashViewModel>(), GenericDialog.GenericDialogListener {

    override val layoutResId: Int
        get() = R.layout.activity_splash

    override fun setViews() {

        viewModel.initGame.observe(this, Observer { shouldInitGame ->
            if(shouldInitGame && viewModel.appContentLoaded && viewModel.animsFinished) {
                prgBarLoadGame.setProgress(1)
                prgBarLoadGame.anim(Preset.SQUEEZE_OUT_DOWN, Curve.BZR_EASE_IN_OUT_SINE, 1000, 500) {
                    prgBarLoadGame.visibility = View.INVISIBLE
                    imgIconApp_1.anim(Preset.FADE_OUT, Curve.BZR_EASE_IN_OUT_SINE, 1000)
                    imgIconApp_2.anim(Preset.FADE_OUT, Curve.BZR_EASE_IN_OUT_SINE, 1000)
                    txtTitleApp.anim(Preset.FADE_OUT, Curve.BZR_EASE_IN_OUT_SINE, 1000)
                    ly_bottom.anim(Preset.SQUEEZE_OUT_DOWN, Curve.BZR_EASE_IN_OUT_SINE, 1000, 200) {
                        Navigator.startMenuActivity(this)
                    }
                }
            }
        })

        viewModel.progress.observe(this, Observer { progress ->
            prgBarLoadGame.setProgress(progress)
        })

        viewModel.errorTag.observe(this, Observer { error_tag ->
            when(error_tag) {
                TAG_GENERIC_ERROR -> {
                    GenericDialog.newInstance(this@SplashActivity,
                        null,
                        getString(R.string.title_generic_error),
                        getString(R.string.desc_generic_error),
                        getString(R.string.try_again),
                        null,
                        TAG_GENERIC_ERROR,
                        R.drawable.ic_warning,
                        cancelable = false)
                }
                TAG_NO_INTERNET_ERROR -> {
                    GenericDialog.newInstance(this@SplashActivity,
                        null,
                        getString(R.string.no_internet),
                        getString(R.string.impossible_connect),
                        getString(R.string.try_again),
                        null,
                        TAG_NO_INTERNET_ERROR,
                        R.drawable.ic_warning,
                        cancelable = false)
                }
                TAG_UPDATE_APP_ERROR -> {
                    GenericDialog.newInstance(this@SplashActivity,
                        null,
                        getString(R.string.update_app_title),
                        getString(R.string.update_app_description),
                        getString(R.string.update_app_button),
                        null,
                        TAG_UPDATE_APP_ERROR,
                        R.drawable.ic_warning,
                        cancelable = false)
                }
            }
        })

        imgIconApp_1.alpha = 0F
        imgIconApp_2.alpha = 0F
        txtTitleApp.visibility = View.GONE

        imgIconApp_1.anim(
            Preset.ZOOM_IN, Curve.BZR_EASE_OUT_SINE, 500, 500) {
            imgIconApp_2.anim(Preset.ZOOM_IN, Curve.BZR_EASE_OUT_SINE, 500) {
                txtTitleApp.visibility = View.VISIBLE
                txtTitleApp.anim(Preset.FADE_IN, Curve.BZR_EASE_OUT_SINE, 500) {
                    viewModel.animsFinished()
                }
            }
        }

        viewModel.getAppVersion()
    }

    override fun onGenericDialogRightClicked(_tag: String) {
        when(_tag) {
            TAG_GENERIC_ERROR -> {
                Navigator.startSplashActivity(this)
            }
            TAG_NO_INTERNET_ERROR -> {
                Navigator.startSplashActivity(this)
            }
            TAG_UPDATE_APP_ERROR -> {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
                }
                this@SplashActivity.finish()
            }
        }
    }

    override fun onGenericDialogLeftClicked(_tag: String) {}
    override fun onGenericDialogDismiss(_tag: String) {}

    companion object {
        const val TAG_GENERIC_ERROR = "tag_generic_error"
        const val TAG_NO_INTERNET_ERROR = "tag_no_interneterror"
        const val TAG_UPDATE_APP_ERROR = "tag_update_app_error"

        fun createIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }
}