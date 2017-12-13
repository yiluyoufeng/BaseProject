package com.pro.feng.myapplication.core;

import com.pro.feng.myapplication.core.bean.ResultMode;
import com.pro.feng.myapplication.core.func.HttpResultErrorFunc;
import com.pro.feng.myapplication.core.func.HttpStringErrorFunc;
import com.pro.feng.myapplication.core.okhttp.callback.JYNetListener;
import com.pro.feng.myapplication.core.subscriber.LoadBarSubscriber;
import com.pro.feng.myapplication.core.subscriber.ResultAuthSubscriber;
import com.pro.feng.myapplication.global.GlobalApplication;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/12/13.
 */

public class NetHelperUtil {

    /**
     * 解析成bean，带Loading对话框
     * @param observable
     * @param listener
     * @param <T>
     * @return
     */
    public static <T> Subscription post(Observable<ResultMode<T>> observable, JYNetListener<T> listener){
        LoadBarSubscriber<T> subscriber = new LoadBarSubscriber<T>(true, GlobalApplication.getContext(),listener);
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultErrorFunc<T>())
                .subscribe(subscriber);
    }

    /**
     * 解析成bean，不需要对话框
     * @param observable
     * @param listener
     * @param <T>
     * @return
     */
    public static <T> Subscription postBackground(Observable<ResultMode<T>> observable, JYNetListener<T> listener){
        ResultAuthSubscriber<T> subscriber = new ResultAuthSubscriber<T>(listener);
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultErrorFunc<T>())
                .subscribe(subscriber);
    }

    /**
     * 解析成string，带Loading对话框
     * @param observable
     * @param listener
     * @return
     */
    public static Subscription postConvert(Observable<String> observable, JYNetListener<String> listener){
        LoadBarSubscriber<String> subscriber = new LoadBarSubscriber<String>(true, GlobalApplication.getContext(),listener);
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpStringErrorFunc())
                .subscribe(subscriber);
    }

    /**
     *解析成string,不带loading 对话框
     * @param observable
     * @param listener
     * @return
     */
    public static Subscription postConvertBackground(Observable<String> observable, JYNetListener<String> listener){
        ResultAuthSubscriber<String> subscriber = new ResultAuthSubscriber<String>(listener);
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpStringErrorFunc())
                .subscribe(subscriber);
    }
}
