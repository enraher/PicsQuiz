package com.pics.flubber;

import android.animation.Animator;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;

import com.pics.flubber.interpolator.providers.Spring;
import com.pics.flubber.interpolator.providers.bezier.BzrSpring;
import com.pics.flubber.interpolator.providers.bezier.EaseIn;
import com.pics.flubber.interpolator.providers.bezier.EaseInBack;
import com.pics.flubber.interpolator.providers.bezier.EaseInCirc;
import com.pics.flubber.interpolator.providers.bezier.EaseInCubic;
import com.pics.flubber.interpolator.providers.bezier.EaseInExpo;
import com.pics.flubber.interpolator.providers.bezier.EaseInOut;
import com.pics.flubber.interpolator.providers.bezier.EaseInOutBack;
import com.pics.flubber.interpolator.providers.bezier.EaseInOutCirc;
import com.pics.flubber.interpolator.providers.bezier.EaseInOutCubic;
import com.pics.flubber.interpolator.providers.bezier.EaseInOutExpo;
import com.pics.flubber.interpolator.providers.bezier.EaseInOutQuad;
import com.pics.flubber.interpolator.providers.bezier.EaseInOutQuart;
import com.pics.flubber.interpolator.providers.bezier.EaseInOutQuint;
import com.pics.flubber.interpolator.providers.bezier.EaseInOutSine;
import com.pics.flubber.interpolator.providers.bezier.EaseInQuad;
import com.pics.flubber.interpolator.providers.bezier.EaseInQuart;
import com.pics.flubber.interpolator.providers.bezier.EaseInQuint;
import com.pics.flubber.interpolator.providers.bezier.EaseInSine;
import com.pics.flubber.interpolator.providers.bezier.EaseOut;
import com.pics.flubber.interpolator.providers.bezier.EaseOutBack;
import com.pics.flubber.interpolator.providers.bezier.EaseOutCirc;
import com.pics.flubber.interpolator.providers.bezier.EaseOutCubic;
import com.pics.flubber.interpolator.providers.bezier.EaseOutExpo;
import com.pics.flubber.interpolator.providers.bezier.EaseOutQuad;
import com.pics.flubber.interpolator.providers.bezier.EaseOutQuart;
import com.pics.flubber.interpolator.providers.bezier.EaseOutQuint;
import com.pics.flubber.interpolator.providers.bezier.EaseOutSine;
import com.pics.flubber.interpolator.providers.bezier.Linear;
import com.pics.flubber.providers.Alpha;
import com.pics.flubber.providers.FadeIn;
import com.pics.flubber.providers.FadeInDown;
import com.pics.flubber.providers.FadeInLeft;
import com.pics.flubber.providers.FadeInRight;
import com.pics.flubber.providers.FadeInUp;
import com.pics.flubber.providers.FadeOut;
import com.pics.flubber.providers.FadeOutIn;
import com.pics.flubber.providers.Fall;
import com.pics.flubber.providers.Flash;
import com.pics.flubber.providers.FlipX;
import com.pics.flubber.providers.FlipY;
import com.pics.flubber.providers.Morph;
import com.pics.flubber.providers.Pop;
import com.pics.flubber.providers.Rotation;
import com.pics.flubber.providers.ScaleX;
import com.pics.flubber.providers.ScaleY;
import com.pics.flubber.providers.Shake;
import com.pics.flubber.providers.SlideDown;
import com.pics.flubber.providers.SlideLeft;
import com.pics.flubber.providers.SlideRight;
import com.pics.flubber.providers.SlideUp;
import com.pics.flubber.providers.Squeeze;
import com.pics.flubber.providers.SqueezeDown;
import com.pics.flubber.providers.SqueezeLeft;
import com.pics.flubber.providers.SqueezeOutDown;
import com.pics.flubber.providers.SqueezeRight;
import com.pics.flubber.providers.SqueezeUp;
import com.pics.flubber.providers.Swing;
import com.pics.flubber.providers.TranslationX;
import com.pics.flubber.providers.TranslationY;
import com.pics.flubber.providers.Wobble;
import com.pics.flubber.providers.ZoomCardIn;
import com.pics.flubber.providers.ZoomIn;
import com.pics.flubber.providers.ZoomOut;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

public class Flubber {
    public static final float[] FRACTIONS = new float[]{0f, 0.2f, 0.4f, 0.6f, 0.8f, 1f};
    public static final String SCALE = "scale";
    private static final String TAG = "Flubber";

    @Contract(" -> !null")
    public static AnimationBody.Builder with() {
        return AnimationBody.Builder.getBuilder();
    }

    public static Animator getAnimation(@NonNull final AnimationBody animationBody, View view) {
        return animationBody.getAnimation().createAnimationFor(animationBody, view);
    }

    public static enum Preset implements AnimationProvider {
        SLIDE_LEFT,
        SLIDE_RIGHT,
        SLIDE_DOWN,
        SLIDE_UP,
        SQUEEZE_LEFT,
        SQUEEZE_RIGHT,
        SQUEEZE_DOWN,
        SQUEEZE_UP,
        SQUEEZE_OUT_DOWN,
        FADE_IN,
        FADE_OUT,
        FADE_OUT_IN,
        FADE_IN_LEFT,
        FADE_IN_RIGHT,
        FADE_IN_DOWN,
        FADE_IN_UP,
        ZOOM_IN,
        ZOOM_CARD_IN,
        ZOOM_OUT,
        FALL,
        SHAKE,
        POP,
        FLIP_X,
        FLIP_Y,
        MORPH,
        SQUEEZE,
        FLASH,
        WOBBLE,
        SWING,
        ALPHA,
        ROTATION,
        TRANSLATION_X,
        TRANSLATION_Y,
        SCALE_X,
        SCALE_Y;

        private static Map<Preset, AnimationProvider> providers = new HashMap<>();

        static {
            providers.put(SLIDE_LEFT, new SlideLeft());
            providers.put(SLIDE_RIGHT, new SlideRight());
            providers.put(SLIDE_DOWN, new SlideDown());
            providers.put(SLIDE_UP, new SlideUp());
            providers.put(SQUEEZE_LEFT, new SqueezeLeft());
            providers.put(SQUEEZE_RIGHT, new SqueezeRight());
            providers.put(SQUEEZE_DOWN, new SqueezeDown());
            providers.put(SQUEEZE_UP, new SqueezeUp());
            providers.put(SQUEEZE_OUT_DOWN, new SqueezeOutDown());
            providers.put(FADE_IN, new FadeIn());
            providers.put(FADE_OUT, new FadeOut());
            providers.put(FADE_OUT_IN, new FadeOutIn());
            providers.put(FADE_IN_LEFT, new FadeInLeft());
            providers.put(FADE_IN_RIGHT, new FadeInRight());
            providers.put(FADE_IN_DOWN, new FadeInDown());
            providers.put(FADE_IN_UP, new FadeInUp());
            providers.put(ZOOM_IN, new ZoomIn());
            providers.put(ZOOM_CARD_IN, new ZoomCardIn());
            providers.put(ZOOM_OUT, new ZoomOut());
            providers.put(FALL, new Fall());
            providers.put(SHAKE, new Shake());
            providers.put(POP, new Pop());
            providers.put(FLIP_X, new FlipX());
            providers.put(FLIP_Y, new FlipY());
            providers.put(MORPH, new Morph());
            providers.put(SQUEEZE, new Squeeze());
            providers.put(FLASH, new Flash());
            providers.put(WOBBLE, new Wobble());
            providers.put(SWING, new Swing());
            providers.put(ALPHA, new Alpha());
            providers.put(ROTATION, new Rotation());
            providers.put(TRANSLATION_X, new TranslationX());
            providers.put(TRANSLATION_Y, new TranslationY());
            providers.put(SCALE_X, new ScaleX());
            providers.put(SCALE_Y, new ScaleY());

            if (providers.keySet().size() != Preset.values().length) {
                throw new IllegalStateException("You haven't registered all providers for the animation preset.");
            }
        }

        public AnimationProvider getProvider() {
            return providers.get(this);
        }

        public Animator createAnimationFor(AnimationBody animationBody, View view) {
            return getProvider().createAnimationFor(animationBody, view);
        }

        public static Preset valueFor(AnimationProvider animationProvider) {
            for (Map.Entry<Preset, AnimationProvider> providersEntry :
                    providers.entrySet()) {

                if (animationProvider.getClass().isAssignableFrom(providersEntry.getValue().getClass())) {
                    return providersEntry.getKey();
                }
            }

            return null;
        }
    }

    public static enum Curve implements InterpolatorProvider {
        BZR_EASE_IN,
        BZR_EASE_OUT,
        BZR_EASE_IN_OUT,
        BZR_LINEAR,
        BZR_SPRING,
        BZR_EASE_IN_SINE,
        BZR_EASE_OUT_SINE,
        BZR_EASE_IN_OUT_SINE,
        BZR_EASE_IN_QUAD,
        BZR_EASE_OUT_QUAD,
        BZR_EASE_IN_OUT_QUAD,
        BZR_EASE_IN_CUBIC,
        BZR_EASE_OUT_CUBIC,
        BZR_EASE_IN_OUT_CUBIC,
        BZR_EASE_IN_QUART,
        BZR_EASE_OUT_QUART,
        BZR_EASE_IN_OUT_QUART,
        BZR_EASE_IN_QUINT,
        BZR_EASE_OUT_QUINT,
        BZR_EASE_IN_OUT_QUINT,
        BZR_EASE_IN_EXPO,
        BZR_EASE_OUT_EXPO,
        BZR_EASE_IN_OUT_EXPO,
        BZR_EASE_IN_CIRC,
        BZR_EASE_OUT_CIRC,
        BZR_EASE_IN_OUT_CIRC,
        BZR_EASE_IN_BACK,
        BZR_EASE_OUT_BACK,
        BZR_EASE_IN_OUT_BACK,
        SPRING,
        LINEAR;

        private static Map<Curve, InterpolatorProvider> providers = new HashMap<>();

        static {
            providers.put(BZR_EASE_IN, new EaseIn());
            providers.put(BZR_EASE_OUT, new EaseOut());
            providers.put(BZR_EASE_IN_OUT, new EaseInOut());
            providers.put(BZR_LINEAR, new Linear());
            providers.put(BZR_SPRING, new BzrSpring());
            providers.put(BZR_EASE_IN_SINE, new EaseInSine());
            providers.put(BZR_EASE_OUT_SINE, new EaseOutSine());
            providers.put(BZR_EASE_IN_OUT_SINE, new EaseInOutSine());
            providers.put(BZR_EASE_IN_QUAD, new EaseInQuad());
            providers.put(BZR_EASE_OUT_QUAD, new EaseOutQuad());
            providers.put(BZR_EASE_IN_OUT_QUAD, new EaseInOutQuad());
            providers.put(BZR_EASE_IN_CUBIC, new EaseInCubic());
            providers.put(BZR_EASE_OUT_CUBIC, new EaseOutCubic());
            providers.put(BZR_EASE_IN_OUT_CUBIC, new EaseInOutCubic());
            providers.put(BZR_EASE_IN_QUART, new EaseInQuart());
            providers.put(BZR_EASE_OUT_QUART, new EaseOutQuart());
            providers.put(BZR_EASE_IN_OUT_QUART, new EaseInOutQuart());
            providers.put(BZR_EASE_IN_QUINT, new EaseInQuint());
            providers.put(BZR_EASE_OUT_QUINT, new EaseOutQuint());
            providers.put(BZR_EASE_IN_OUT_QUINT, new EaseInOutQuint());
            providers.put(BZR_EASE_IN_EXPO, new EaseInExpo());
            providers.put(BZR_EASE_OUT_EXPO, new EaseOutExpo());
            providers.put(BZR_EASE_IN_OUT_EXPO, new EaseInOutExpo());
            providers.put(BZR_EASE_IN_CIRC, new EaseInCirc());
            providers.put(BZR_EASE_OUT_CIRC, new EaseOutCirc());
            providers.put(BZR_EASE_IN_OUT_CIRC, new EaseInOutCirc());
            providers.put(BZR_EASE_IN_BACK, new EaseInBack());
            providers.put(BZR_EASE_OUT_BACK, new EaseOutBack());
            providers.put(BZR_EASE_IN_OUT_BACK, new EaseInOutBack());
            providers.put(SPRING, new Spring());
            providers.put(LINEAR, new Linear());

            if (providers.keySet().size() != Curve.values().length) {
                throw new IllegalStateException("You haven't registered all providers for all curves.");
            }
        }

        public InterpolatorProvider getProvider() {
            return providers.get(this);
        }

        @Override
        public Interpolator createInterpolatorFor(AnimationBody animationBody) {
            return getProvider().createInterpolatorFor(animationBody);
        }

        public static Curve valueFor(InterpolatorProvider interpolatorProvider) {
            for (Map.Entry<Curve, InterpolatorProvider> providersEntry :
                    providers.entrySet()) {

                if (interpolatorProvider.getClass().isAssignableFrom(providersEntry.getValue().getClass())) {
                    return providersEntry.getKey();
                }
            }

            return null;
        }
    }

    public static interface AnimationProvider {
        public Animator createAnimationFor(final AnimationBody animationBody, View view);
    }

    public static interface InterpolatorProvider {
        public Interpolator createInterpolatorFor(final AnimationBody animationBody);
    }

}
