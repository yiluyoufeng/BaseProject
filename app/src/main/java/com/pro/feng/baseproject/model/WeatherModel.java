package com.pro.feng.baseproject.model;

import com.pro.feng.baseproject.api.ApiService;
import com.pro.feng.baseproject.api.WeatherBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Feng on 2017/12/12.
 */

public class WeatherModel {
    String baseUrl = "http://www.weather.com.cn/";

    public void requestData(String cityId, Callback<WeatherBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                //代表root地址
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        //请求
        Call<WeatherBean> weatherBeanCall = apiService.requestWeather(cityId);
        weatherBeanCall.enqueue(callback);
    }
}
