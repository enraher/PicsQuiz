package com.pics.flubber.providers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.pics.flubber.AnimationBody;


public class TranslationY extends BaseProvider {
    @Override
    public Animator getAnimationFor(AnimationBody animationBody, View view) {
        return ObjectAnimator.ofFloat(
                view,
                View.TRANSLATION_Y,
                animationBody.getStartY(),
                animationBody.getEndY());
    }
}
