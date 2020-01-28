package com.pics.flubber.interpolator.providers;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.pics.flubber.AnimationBody;
import com.pics.flubber.Flubber;

public class Linear implements Flubber.InterpolatorProvider {
    @Override
    public Interpolator createInterpolatorFor(AnimationBody animationBody) {
        return new LinearInterpolator();
    }
}
