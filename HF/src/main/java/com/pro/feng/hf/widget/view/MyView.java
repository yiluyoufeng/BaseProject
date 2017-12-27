package com.pro.feng.hf.widget.view;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pro.feng.hf.utils.LogUtils;

/**
 * Created by Feng on 2017/12/26.
 */

public class MyView extends View {
    public static final String TAG = "View";

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtils.e(TAG+"--------dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e(TAG+"--------onTouchEvent");
        return super.onTouchEvent(event);
    }


}
