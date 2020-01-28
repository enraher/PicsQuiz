package com.pics.flubber.interpolator.providers;

import android.view.animation.Interpolator;

import com.pics.flubber.AnimationBody;
import com.pics.flubber.Flubber;
import com.pics.flubber.interpolator.SpringInterpolator;

public class Spring implements Flubber.InterpolatorProvider {
    @Override
    public Interpolator createInterpolatorFor(AnimationBody animationBody) {
        final float damping = animationBody.getDamping();
        final float velocity = animationBody.getVelocity();

        return new SpringInterpolator(damping, velocity);
    }
}
