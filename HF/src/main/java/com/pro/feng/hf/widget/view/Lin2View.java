package com.pro.feng.hf.widget.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.pro.feng.hf.utils.LogUtils;

/**
 * Created by Feng on 2017/12/26.
 */

public class Lin2View extends LinearLayout{
    public static final String TAG = "Lin2";

    public Lin2View(Context context) {
        super(context);
    }

    public Lin2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.e(TAG+"--------dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.e(TAG+"--------onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e(TAG+"--------onTouchEvent");
        return super.onTouchEvent(event);
    }
}
