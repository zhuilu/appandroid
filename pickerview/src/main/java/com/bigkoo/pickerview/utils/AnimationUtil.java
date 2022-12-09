package com.bigkoo.pickerview.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;

public class AnimationUtil {

    //动画持续时间
    public final static int ANIMATION_IN_TIME=200;
    public final static int ANIMATION_OUT_TIME=200;

    public static void createAnimation(final boolean isIn, final View rootView, final View target,
                                       final AnimInterface animInterface){
        final int toYDelta = ViewUtils.getViewMeasuredHeight(target);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(isIn?-toYDelta:0,isIn?0:-toYDelta);
        valueAnimator.setDuration(isIn?ANIMATION_IN_TIME:ANIMATION_OUT_TIME);
        valueAnimator.setRepeatCount(0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentValue = (Float) valueAnimator.getAnimatedValue();
                target.setY(currentValue);
                if (!isIn)//因为在setAnimationStyle(R.style.SelectPopupWindow);设置了进入动画,所以执行进入动画时不再设置
                    rootView.setAlpha(1-Math.abs(currentValue)/valueAnimator.getDuration());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animInterface!=null)
                    animInterface.animEnd();
            }
        });
        valueAnimator.start();
    }

    public static void startAtArchor(final boolean isIn, final View rootView, final View target,
                                       final AnimInterface animInterface){
        final int toYDelta = ViewUtils.getViewMeasuredHeight(target);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(isIn?-toYDelta:0,isIn?0:-toYDelta);
        valueAnimator.setDuration(isIn?ANIMATION_IN_TIME:ANIMATION_OUT_TIME);
        valueAnimator.setRepeatCount(0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentValue = (Float) valueAnimator.getAnimatedValue();
                target.setY(currentValue);
                if (!isIn)//因为在setAnimationStyle(R.style.SelectPopupWindow);设置了进入动画,所以执行进入动画时不再设置
                    rootView.setAlpha(1-Math.abs(currentValue)/valueAnimator.getDuration());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animInterface!=null)
                    animInterface.animEnd();
            }
        });
        valueAnimator.start();
    }
    public interface AnimInterface{
        void animEnd();
    }
}