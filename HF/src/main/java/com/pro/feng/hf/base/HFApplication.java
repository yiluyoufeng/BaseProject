package com.pro.feng.hf.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Feng on 2017/12/18.
 */

public class HFApplication extends Application{
    public static Context context;
    public static int mainThreadId;
    public static HFApplication mApp;
    public Activity curActivity;

    public static synchronized HFApplication getInstance() {
        if (mApp == null) {
            mApp = new HFApplication();
        }
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        context = getApplicationContext();
        mainThreadId = android.os.Process.myTid();
        AppStatusTracker.init(this);
    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

}
