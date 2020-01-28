package com.pics.flubber.providers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.pics.flubber.AnimationBody;

public class FadeIn extends BaseProvider {

    @Override
    public Animator getAnimationFor(AnimationBody animationBody, View view) {

        final ObjectAnimator animation = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f);

        return animation;

    }
}
