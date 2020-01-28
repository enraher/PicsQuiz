package com.pics.flubber.providers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

import com.pics.flubber.AnimationBody;

public class FlipX extends BaseProvider {

    @Override
    public Animator getAnimationFor(AnimationBody animationBody, View view) {

        final float startRotation = view.getRotationX();
        final float endRotation = startRotation + 180f;

        final PropertyValuesHolder rotationPVH =
                PropertyValuesHolder.ofFloat(View.ROTATION_X, startRotation, endRotation);

        final ObjectAnimator animation =
                ObjectAnimator.ofPropertyValuesHolder(view, rotationPVH);

        return animation;
    }
}
