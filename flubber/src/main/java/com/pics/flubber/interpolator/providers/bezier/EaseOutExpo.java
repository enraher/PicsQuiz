package com.pics.flubber.interpolator.providers.bezier;

import android.view.animation.Interpolator;

import androidx.core.view.animation.PathInterpolatorCompat;

import com.pics.flubber.AnimationBody;
import com.pics.flubber.Flubber;

public class EaseOutExpo implements Flubber.InterpolatorProvider {
    @Override
    public Interpolator createInterpolatorFor(AnimationBody animationBody) {
        return PathInterpolatorCompat.create(0.19f, 1f, 0.22f, 1f);
    }
}
