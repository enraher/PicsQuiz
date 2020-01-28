package com.pics.flubber.providers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

import com.pics.flubber.AnimationBody;
import com.pics.flubber.Flubber;
import com.pics.flubber.utils.DimensionUtils;

public class SlideDown extends BaseProvider {

    public SlideDown() {
        super(Flubber.Curve.SPRING);
    }

    @Override
    public Animator getAnimationFor(AnimationBody animationBody, View view) {

        final float startY = DimensionUtils.dp2px(-800);
        final float endY = 0f;

        final PropertyValuesHolder translationPVH =
                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, startY, endY);

        final ObjectAnimator animation =
                ObjectAnimator.ofPropertyValuesHolder(view, translationPVH);

        return animation;
    }
}
