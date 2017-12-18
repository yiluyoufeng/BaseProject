package com.pro.feng.rx;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/12/14.
 */

public class ScheduleHelper<T> {

    public static void controlSchedule() {
        //被观察者在主线程中，每1ms发送一个事件
        Observable.interval(1, TimeUnit.MILLISECONDS)
                //.subscribeOn(Schedulers.newThread())
                // 将观察者的工作放在新线程环境中

                .observeOn(Schedulers.newThread())//观察者处理每1000ms才处理一个事件
                .subscribe(new Action1<Long>() {

                    @Override
                    public void call(Long aLong) {
                        try {
                            Thread.sleep(1000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.w("TAG", "---->" + aLong);
                    }
                });




    }
}
