package com.pro.feng.myapplication.core.okhttp.callback;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Feng on 2016/11/7.
 * 文件上传回调
 */
public abstract class RetrofitCallBack<T> implements Callback<T>,Serializable {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()) {
            onSuccess(call, response);
            finish();
        } else {
            onFailure(call, new Throwable(response.message()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        finish();
    }

    public abstract void onSuccess(Call<T> call, Response<T> response);

    public void onLoading(int progress){

    }

    public void onLoading(long total,long progress){
        int currentProgress = (int)(progress * 100.0 / total);

    }

    public void onLoading(long total,long progress, boolean ok){

    }

    /**
     * 不管成功失败,执行完回调
     */
    public void finish(){
    }

}
