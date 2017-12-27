package com.pro.feng.hf.core.http;

import com.pro.feng.hf.base.HFApplication;
import com.pro.feng.hf.core.http.bean.ResultMode;
import com.pro.feng.hf.core.http.func.HttpResultErrorFunc;
import com.pro.feng.hf.core.http.func.HttpStringErrorFunc;
import com.pro.feng.hf.core.http.okhttp.callback.HFNetConsumer;
import com.pro.feng.hf.core.http.okhttp.callback.HFNetListener;
import com.pro.feng.hf.core.http.subscriber.LoadBarObserver;
import com.pro.feng.hf.core.http.subscriber.ResultAuthObserver;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Feng on 2017/12/13.
 */

public class NetHelperUtil {

    /**
     * 解析成bean，带Loading对话框
     * 格式按照此格式  {result  msg  data } 把 data 解析成 T
     * @param observable
     * @param listener
     * @param <T>
     * @return
     */
    public static <T> void post(Observable<ResultMode<T>> observable, HFNetListener<T> listener){
        LoadBarObserver<T> observer = new LoadBarObserver<T>(true, HFApplication.getInstance().curActivity,listener);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultErrorFunc())
                .subscribe(observer);
    }

    /**
     * 解析成bean，不需要对话框
     * @param observable
     * @param listener
     * @param <T>
     * @return
     */
    public static <T> Disposable postBackground(Observable<ResultMode<T>> observable, HFNetConsumer<T> listener){
        Consumer<Throwable> error = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(listener!=null){
                    listener.error(throwable);
                }
            }
        };
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultErrorFunc<T>())
                .subscribe(listener,error);
    }

    /**
     * 解析成string，带Loading对话框
     * 格式按照此格式  {result  msg  data }
     * @param observable
     * @param listener
     * @return
     */
    public static void postConvert(Observable<String> observable, HFNetListener<String> listener){
        LoadBarObserver<String> observer = new LoadBarObserver<String>(true, HFApplication.getInstance().curActivity,listener);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpStringErrorFunc())
                .subscribe(observer);
    }

    /**
     *解析成string,不带loading 对话框
     * 格式按照此格式  {result  msg  data }
     * @param observable
     * @param listener
     * @return
     */
    public static Disposable postConvertBack(Observable<String> observable, HFNetConsumer<String> listener){
        Consumer<Throwable> error = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(listener!=null){
                    listener.error(throwable);
                }
            }
        };
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpStringErrorFunc())
                .subscribe(listener,error);
    }

    /**
     * 直接解析成bean，不需要对话框
     * @param observable
     * @param listener
     * @param <T>
     * @return
     */
    public static <T> Disposable postToBeanBack(Observable<T> observable, HFNetConsumer<T> listener){
        Consumer<Throwable> error = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(listener!=null){
                    listener.error(throwable);
                }
            }
        };
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener,error);
    }

    /**
     * 直接解析成bean，需要对话框
     * @param observable
     * @param listener
     * @param <T>
     * @return
     */
    public static <T> void postToBean(Observable<T> observable, HFNetListener<T> listener){
        LoadBarObserver<T> observer = new LoadBarObserver<T>(true, HFApplication.getInstance().curActivity,listener);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
