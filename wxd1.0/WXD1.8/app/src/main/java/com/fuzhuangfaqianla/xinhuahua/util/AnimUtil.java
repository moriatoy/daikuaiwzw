package com.fuzhuangfaqianla.xinhuahua.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by xiongchang on 2017/10/13.
 */

public class AnimUtil {

    public static void showWithTranslate(View view, Animation.AnimationListener animationListener){
        TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translate.setDuration(300);//动画时间500毫秒
        translate.setFillAfter(true);//动画出来控件可以点击
        translate.setAnimationListener(animationListener);
        view.startAnimation(translate);//开始动画
    }

    public static void hideWithTranslate(View view, Animation.AnimationListener animationListener){
        TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        translate.setDuration(300);//动画时间500毫秒
        translate.setFillAfter(false);//动画出来控件可以点击
        translate.setAnimationListener(animationListener);
        view.startAnimation(translate);//开始动画
    }
}
