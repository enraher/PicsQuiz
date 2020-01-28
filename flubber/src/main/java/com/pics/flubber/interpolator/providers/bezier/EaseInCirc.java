package com.pics.flubber.interpolator.providers.bezier;

import android.view.animation.Interpolator;

import androidx.core.view.animation.PathInterpolatorCompat;

import com.pics.flubber.AnimationBody;
import com.pics.flubber.Flubber;

public class EaseInCirc implements Flubber.InterpolatorProvider {
    @Override
    public Interpolator createInterpolatorFor(AnimationBody animationBody) {
        return PathInterpolatorCompat.create(0.6f, 0.04f, 0.98f, 0.335f);
    }
}
