package com.pics.quiz.extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.addListener
import com.pics.flubber.Flubber
import com.pics.quiz.R
import com.pics.quiz.other.Constants
import com.pics.quiz.other.getCurrentLanguage
import com.squareup.picasso.Picasso
import java.io.File

fun View.anim(animationProvider: Flubber.AnimationProvider, interpolatorProvider: Flubber.InterpolatorProvider,
              duration: Long, delay: Long = 0, repeatCount: Int = 0, repeatMode: Int = ValueAnimator.REVERSE, forceToShow: Boolean = false, onEnd: () -> Unit = { })  {
    if(forceToShow || !Constants.DISABLE_ANIMS) {
        val animation: Animator = Flubber.with()
            .animation(animationProvider)
            .interpolator(interpolatorProvider)
            .duration(duration)
            .repeatCount(repeatCount)
            .repeatMode(repeatMode)
            .delay(delay)
            .createFor(this)
        animation.addListener(onEnd = { onEnd() })
        animation.start()
    } else {
        this.visibility = View.VISIBLE
        this.alpha = 1F
        onEnd()
    }
}

fun AppCompatImageView.loadImageFromMemory(dir: String?, idImage: String) {
    val f = File(context.filesDir, "$dir/$idImage.jpg")
    if(f.exists()) {
        Picasso.get().load(f).into(this)
    } else {
        this.setImageResource(R.drawable.ic_empty)
    }
}

fun AppCompatTextView.loadName(name: Map<String, String>) {
    val language = getCurrentLanguage(context)
    this.text = if(name.containsKey(language) ) {
        if(name[language]?.isNotEmpty()!!) {
            name[language]
        } else {
            name["en"]
        }
    } else {
        name["en"]
    }
}