package com.pro.feng.myapplication.core;

import com.pro.feng.myapplication.core.okhttp.convert.ToStringConverterFactory;
import com.pro.feng.myapplication.core.retrofit.RetrofitCreateHelper;

/**
 * Created by Feng on 2017/12/13.
 */

public class ApiManager {
    private static ApiService apiService;

    public static ApiService getApi() {
        apiService = new RetrofitCreateHelper.Builder<ApiService>().baseUrl("").build(ApiService.class);
        return apiService;
    }

    public static ApiService getConvertApi() {
        apiService = new RetrofitCreateHelper.Builder<ApiService>().baseUrl("").addConverterFactory(new ToStringConverterFactory()).build(ApiService.class);
        return apiService;
    }
}
