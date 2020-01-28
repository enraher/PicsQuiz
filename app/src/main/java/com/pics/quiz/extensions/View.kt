package com.pics.quiz.extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.addListener
import com.pics.flubber.Flubber
import com.pics.quiz.other.Constants

fun View.anim(animationProvider: Flubber.AnimationProvider, interpolatorProvider: Flubber.InterpolatorProvider,
              duration: Long, delay: Long = 0, repeatCount: Int = 0, repeatMode: Int = ValueAnimator.REVERSE, forceToShow: Boolean = false, onEnd: () -> Unit = { })  {
    if(forceToShow || Constants.DISABLE_ANIMS) {
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