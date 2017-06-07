package cn.zplayer.mc.framework.controller;

import android.view.View;

/**
 * Created by Administrator on 2016/4/6.
 *
 * 防止 快速点击
 *
 * windowIsTranslucent 设置true 会出现连点两次的bug
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 600;

    private long lastClickTime = 0;
    private long currentTime = 0;

    @Override
    public void onClick(View v) {
        currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    public abstract void onNoDoubleClick(View v);
}
