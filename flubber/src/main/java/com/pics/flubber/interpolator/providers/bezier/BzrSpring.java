package com.pics.flubber.interpolator.providers.bezier;


import android.view.animation.Interpolator;

import androidx.core.view.animation.PathInterpolatorCompat;

import com.pics.flubber.Flubber;
import com.pics.flubber.AnimationBody;

public class BzrSpring implements Flubber.InterpolatorProvider {
    @Override
    public Interpolator createInterpolatorFor(AnimationBody animationBody) {
        final float force = animationBody.getForce();
        return PathInterpolatorCompat.create(0.5f, 1.1f + force / 3, 1f, 1f);
    }
}
