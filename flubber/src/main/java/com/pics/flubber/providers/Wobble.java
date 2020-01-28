package com.pics.flubber.providers;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.pics.flubber.AnimationBody;
import com.pics.flubber.Flubber;
import com.pics.flubber.utils.DimensionUtils;
import com.pics.flubber.utils.KeyFrameUtil;

import static com.pics.flubber.Flubber.FRACTIONS;


public class Wobble extends BaseProvider {

    @Override
    public Animator createAnimationFor(AnimationBody animationBody, View view) {
        initInterpolatorFor(animationBody);
        return getAnimationFor(animationBody, view);
    }

    @Override
    public Animator getAnimationFor(AnimationBody animationBody, View view) {
        final AnimatorSet animatorSet = new AnimatorSet();

        final Animator rotationAnimator = getRotation(animationBody, view);
        final Animator translationAnimator = getShake(animationBody, view);

        animatorSet.play(translationAnimator)
                .with(rotationAnimator);

        return animatorSet;
    }

    private Animator getRotation(AnimationBody animationBody, View view) {
        final float force = animationBody.getForce();

        float[] rotationValues = {
                (float) Math.toDegrees(0),
                (float) Math.toDegrees(0.3f * force),
                (float) Math.toDegrees(-0.3f * force),
                (float) Math.toDegrees(0.3f * force),
                (float) Math.toDegrees(0f),
                (float) Math.toDegrees(0f)
        };

        final PropertyValuesHolder pvhRotation =
                PropertyValuesHolder.ofKeyframe(View.ROTATION, KeyFrameUtil.getKeyFrames(FRACTIONS, rotationValues));

        final ObjectAnimator animation =
                ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation);

        animation.setInterpolator(new LinearInterpolator());

        return animation;
    }

    private Animator getShake(AnimationBody animationBody, View view) {
        final float dX = DimensionUtils.dp2px(30);
        final float force = animationBody.getForce();

        float[] translationValues = {0f, (dX * force), (-dX * force), (dX * force), 0f, 0f};
        final PropertyValuesHolder translationPVH =
                PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X, KeyFrameUtil.getKeyFrames(FRACTIONS, translationValues));

        final ObjectAnimator animation =
                ObjectAnimator.ofPropertyValuesHolder(view, translationPVH);

        animation.setInterpolator(getInterpolatorProvider().createInterpolatorFor(animationBody));

        return animation;
    }
}
