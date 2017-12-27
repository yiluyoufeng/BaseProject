package com.pro.feng.hf.core.http.subscriber;


import com.pro.feng.hf.core.http.okhttp.callback.HFNetListener;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Feng on 2016/6/12.
 */

public class ResultAuthObserver<T> implements Observer<T> {
    protected HFNetListener<T> netListener;

    public ResultAuthObserver(){
    }

    public ResultAuthObserver(HFNetListener<T> listener){
        this.netListener = listener;
    }

    @Override
    public void onError(Throwable e) {
        if(netListener!=null){
            netListener.onError(e);
        }
    }

    @Override
    public void onComplete() {
        if(netListener!=null){
            netListener.onCompleted();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if(netListener!=null){
            netListener.onNetSuccess(t);
        }
    }
}
