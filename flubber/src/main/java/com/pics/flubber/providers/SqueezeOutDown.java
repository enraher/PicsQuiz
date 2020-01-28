package com.pics.flubber.providers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.pics.flubber.AnimationBody;
import com.pics.flubber.Flubber;
import com.pics.flubber.utils.DimensionUtils;

public class SqueezeOutDown extends BaseProvider {

    public SqueezeOutDown() {
        super(Flubber.Curve.SPRING);
    }

    @Override
    public Animator getAnimationFor(AnimationBody animationBody, View view) {


        final float  endY = DimensionUtils.dp2px(300) * animationBody.getForce();
        final float startY = 0f;

        final ObjectAnimator translateAnimation =
                ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, startY, endY);

        return translateAnimation;
    }
}
