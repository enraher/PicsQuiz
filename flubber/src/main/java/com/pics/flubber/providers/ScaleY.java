package com.pics.flubber.providers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.pics.flubber.AnimationBody;

public class ScaleY extends BaseProvider {
    @Override
    public Animator getAnimationFor(AnimationBody animationBody, View view) {
        return ObjectAnimator.ofFloat(
                view,
                View.SCALE_Y,
                animationBody.getStartScaleY(),
                animationBody.getEndScaleY());
    }
}
