package com.pics.flubber.providers;

import android.animation.ObjectAnimator;
import android.view.View;

import androidx.annotation.NonNull;

import com.pics.flubber.AnimationBody;
import com.pics.flubber.utils.DimensionUtils;

public class FadeInRight extends BaseFadeIn {

    @NonNull
    protected ObjectAnimator getTranslation(AnimationBody animationBody, View view) {
        final float startY = -DimensionUtils.dp2px(300) * animationBody.getForce();
        final float endY = 0;

        final ObjectAnimator translateAnimation =
                ObjectAnimator.ofFloat(view, View.TRANSLATION_X, startY, endY);

        return translateAnimation;
    }

}
