package com.pics.flubber.interpolator.providers.bezier;

import android.view.animation.Interpolator;

import androidx.core.view.animation.PathInterpolatorCompat;

import com.pics.flubber.AnimationBody;
import com.pics.flubber.Flubber;

public class EaseOutSine implements Flubber.InterpolatorProvider {
    @Override
    public Interpolator createInterpolatorFor(AnimationBody animationBody) {
        return PathInterpolatorCompat.create(0.39f, 0.575f, 0.565f, 1f);
    }
}
