package com.pics.flubber.providers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

import com.pics.flubber.AnimationBody;
import com.pics.flubber.Flubber;
import com.pics.flubber.utils.KeyFrameUtil;

import static android.animation.PropertyValuesHolder.ofKeyframe;

public class Morph extends BaseProvider {

    @Override
    public Animator getAnimationFor(AnimationBody animationBody, View view) {
        final float force = animationBody.getForce();

        float[] valuesX = {1f, 1.3f * force, 0.7f, 1.3f * force, 1f, 1f};
        float[] valuesY = {1f, 0.7f, 1.3f * force, 0.7f, 1f, 1f};

        final PropertyValuesHolder scaleXPVH =
                ofKeyframe(View.SCALE_X, KeyFrameUtil.getKeyFrames(Flubber.FRACTIONS, valuesX));

        final PropertyValuesHolder scaleYPVH =
                ofKeyframe(View.SCALE_Y, KeyFrameUtil.getKeyFrames(Flubber.FRACTIONS, valuesY));

        final ObjectAnimator animation =
                ObjectAnimator.ofPropertyValuesHolder(view, scaleXPVH, scaleYPVH);

        return animation;
    }
}
