package com.pro.feng.baseproject.contact;

import com.pro.feng.baseproject.api.WeatherBean;
import com.pro.feng.baseproject.model.WeatherModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Feng on 2017/12/12.
 */

public class RequestPresenter {

    private WeatherModel model;
    private RequestInterface viewOption;

    //P 层负责连接M层与V层，M层与V层完全解耦
    /***
     *  在这个实例中，P层持有V层（Activity等）的实例，当关闭应用，而网络没有请求完成，就会造成内存泄漏
     *  */
    public RequestPresenter(RequestInterface viewOption) {
        this.viewOption = viewOption;
        model = new WeatherModel();
    }

    public void requestData(String city){
        viewOption.requestLoading();
        model.requestData(city, new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                viewOption.requestResult(response.body());
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {
                viewOption.requestFailure("请求失败！！！");
            }
        });
    }
}
