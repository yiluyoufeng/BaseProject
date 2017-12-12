package com.pro.feng.baseproject.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Feng on 2017/12/12.
 */

public interface ApiService {

    /**
     * 请求天气接口
     * @param cityId 城市
     * @return Call
     */
    @GET("data/cityinfo/{cityId}.html")
    Call<WeatherBean> requestWeather(@Path("cityId") String cityId);
}
