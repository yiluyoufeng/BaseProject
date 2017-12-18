package com.pro.feng.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Observable<String> observable;
    Observer<String> observer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("-----observer", s);
            }
        };



        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello work !!!");
                subscriber.onCompleted();
            }
        });
        Subscription subscription = Observable.just("Hello subscription")
                .subscribe(observer);
        Subscription subscription1 = observable.subscribe(observer);
        Log.e("-------", subscription.isUnsubscribed() + "---" + subscription1.isUnsubscribed());
        subscription.unsubscribe();
        subscription1.unsubscribe();
        Log.e("-------", subscription.isUnsubscribed() + "---" + subscription1.isUnsubscribed());

        ScheduleHelper.controlSchedule();

//        createObservable();
//        createObserver();
//        scheduleOption();
    }

    private void scheduleOption() {
        observable.subscribeOn(Schedulers.io())/**控制OnSubscribe被激活所处线程*/
                .observeOn(AndroidSchedulers.mainThread())/**控制Observer 或 Subscriber 所运行的线程*/
                .subscribe(observer);
    }

    private void createObserver() {
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String o) {

            }
        };


        Action1<String> ac1 = new Action1<String>() {
            @Override
            public void call(String o) {

            }
        };

        observable.subscribe(observer);
        observable.subscribe(subscriber);
        observable.subscribe(ac1);
    }

    private void createObservable() {
        /***
         * 创建Observable通过create方法，传入OnSubscribe作为参数
         * 该OnSubscribe对象会存储在Observable中，当发生订阅时，就会调用OnSubscribe的call()方法
         */
        observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onStart();
                subscriber.onNext("");
                subscriber.onError(new RuntimeException());
                subscriber.onCompleted();
            }
        });

        Observable tempObservable = null;
        tempObservable = Observable.just("", "");
        tempObservable = Observable.from(new ArrayList());
        tempObservable = Observable.empty();
        tempObservable = Observable.timer(10, TimeUnit.SECONDS);
        tempObservable = Observable.interval(10, TimeUnit.SECONDS);
        tempObservable = Observable.range(10, 5);
    }


}
