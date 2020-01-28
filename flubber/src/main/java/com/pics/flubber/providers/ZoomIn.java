package com.pics.flubber.providers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

import com.pics.flubber.AnimationBody;

public class ZoomIn extends BaseProvider {

    @Override
    public Animator getAnimationFor(AnimationBody animationBody, View view) {

        final float scale = 5 * animationBody.getForce();

        final PropertyValuesHolder alphaPVH = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f);
        final PropertyValuesHolder scaleXPVH = PropertyValuesHolder.ofFloat(View.SCALE_X, scale, 1f);
        final PropertyValuesHolder scaleYPVH = PropertyValuesHolder.ofFloat(View.SCALE_Y, scale, 1f);

        final ObjectAnimator animation =
                ObjectAnimator.ofPropertyValuesHolder(view, alphaPVH, scaleXPVH, scaleYPVH);

        return animation;
    }
}
