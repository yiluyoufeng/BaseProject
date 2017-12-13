package com.pro.feng.myapplication.core.subscriber;



import com.pro.feng.myapplication.core.okhttp.callback.JYNetListener;

import rx.Subscriber;

/**
 * Created by Feng on 2016/6/12.
 */
public class ResultAuthSubscriber<T> extends Subscriber<T> {
    protected JYNetListener<T> netListener;

    public ResultAuthSubscriber(){
    }

    public ResultAuthSubscriber(JYNetListener<T> listener){
        this.netListener = listener;
    }

    @Override
    public void onCompleted() {
        if(netListener!=null){
            netListener.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        if(netListener!=null){
            netListener.onError(e);
        }
    }

    @Override
    public void onNext(T t) {
        if(netListener!=null){
            netListener.onNetSuccess(t);
        }
    }
}
