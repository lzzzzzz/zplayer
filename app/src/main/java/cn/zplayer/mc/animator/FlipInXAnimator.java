package cn.zplayer.mc.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by Administrator on 2016/1/25 0025.
 */
public class FlipInXAnimator {

    public void setInAnimation(View view) {
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(500);
        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view, "translationY", 180, 90, 60, 30, 0));
        mAnimatorSet.start();
    }

    public AnimatorSet setOutAnimation(View view) {
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(500);
        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view, "translationY", 0, 30, 60, 90, 180));
        mAnimatorSet.start();
        return mAnimatorSet;
    }


    public AnimatorSet setFlyInAnimation(final View view) {
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.8f, 0.6f, 0.4f, 0.2f, 0f), ObjectAnimator.ofFloat(view, "translationY", 0, -50, -80, -100, -140, -160));
        mAnimatorSet.start();
        return mAnimatorSet;
    }


    public void bounceInUpAnimator(final View target) {
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(500);
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                ObjectAnimator.ofFloat(target, "translationY", -target.getHeight() * 3, 0));
        mAnimatorSet.start();
    }

    public void bounceInDownAnimator(View target) {
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(1500);
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(target, "translationY", target.getHeight(), 0));
        mAnimatorSet.start();
    }


    public AnimatorSet transitionAnimator(View target) {
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(500);
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 1, 0f),
                ObjectAnimator.ofFloat(target, "translationX", 0, -target.getMeasuredWidth()));
        mAnimatorSet.start();
        return mAnimatorSet;
    }

}