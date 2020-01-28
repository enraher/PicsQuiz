package com.pics.flubber.interpolator.providers.bezier;

import android.view.animation.Interpolator;

import androidx.core.view.animation.PathInterpolatorCompat;

import com.pics.flubber.AnimationBody;
import com.pics.flubber.Flubber;

public class EaseInQuad implements Flubber.InterpolatorProvider {
    @Override
    public Interpolator createInterpolatorFor(AnimationBody animationBody) {
        return PathInterpolatorCompat.create(0.55f, 0.085f, 0.68f, 0.53f);
    }
}
