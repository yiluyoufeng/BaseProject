package com.pro.feng.myapplication.core.okhttp.callback;


import com.pro.feng.myapplication.core.error.ApiException;

/**
 * Created by Feng on 2017/3/7.
 * 网络请求结果回调
 */

public abstract class JYNetListener<T> {
    public abstract void onNetSuccess(T entity);

    public void onError(Throwable e) {
        //TODO----处理其他错误
        if (e instanceof ApiException) {
            if (((ApiException) e).getResult() == -1) {


            } else if (e instanceof ApiException && ((ApiException) e).getResult() == ApiException.NO_FUN_RESULT){

            }
        } else {//(e instanceof SocketTimeoutException || e instanceof ConnectException || e instanceof SocketException) UnknownHostException

        }
        e.printStackTrace();
    }

    public void onCompleted() {
    }
}
