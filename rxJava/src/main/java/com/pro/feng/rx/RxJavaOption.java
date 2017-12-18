package com.pro.feng.rx;


import android.util.Log;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Feng on 2017/12/14.
 */

public class RxJavaOption {

    public void optionExample(){
        Observable.just("Hello World !!")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                });

        Observable.just("Hello World !!")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer s) {
                        Log.e("---",s+"");
                    }
                });


        Observable.just("Hello World !!")
                .flatMap(new Func1<String, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(String s) {
                        return Observable.from(new ArrayList<Integer>());
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer o) {

            }
        });


    }
}
