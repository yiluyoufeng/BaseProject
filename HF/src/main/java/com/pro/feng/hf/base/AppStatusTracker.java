package com.pro.feng.hf.base;

import android.app.Activity;
import android.os.Bundle;


/**
 * Created by Feng on 2017/12/7.
 */

public class AppStatusTracker implements HFApplication.ActivityLifecycleCallbacks {

    private static final long MAX_INTERVAL = 5 * 60 * 1000;
    private static AppStatusTracker tracker;
    private HFApplication application;
    private boolean isForground;
    private int activeCount;
    private long timestamp;


    private AppStatusTracker(HFApplication application) {
        this.application = application;
        application.registerActivityLifecycleCallbacks(this);
    }

    public static void init(HFApplication application) {
        tracker = new AppStatusTracker(application);
    }

    public static AppStatusTracker getInstance() {
        return tracker;
    }

    public boolean isForground() {
        return isForground;
    }



    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activeCount == 0){
            timestamp = System.currentTimeMillis();
        }
        activeCount++;
        application.curActivity = activity;

        isForground = true;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        activeCount--;
        if (activeCount == 0) {
            isForground = false;
            timestamp = System.currentTimeMillis() - timestamp;
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activeCount--;
        if (activeCount == 0) {
            isForground = false;
            timestamp = System.currentTimeMillis() - timestamp;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activeCount == 0){
            timestamp = System.currentTimeMillis();
        }
        activeCount++;
        application.curActivity = activity;
    }
}