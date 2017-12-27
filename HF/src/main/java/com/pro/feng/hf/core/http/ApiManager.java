package com.pro.feng.hf.core.http;


import com.pro.feng.hf.core.http.okhttp.convert.ToStringConverterFactory;
import com.pro.feng.hf.core.http.retrofit.RetrofitCreateHelper;

/**
 * Created by Feng on 2017/12/13.
 */

public class ApiManager {
    private static ApiService apiService;

    public static ApiService getApi(String baseUrl) {
        apiService = new RetrofitCreateHelper.Builder<ApiService>().baseUrl(baseUrl).build(ApiService.class);
        return apiService;
    }

    public static ApiService getConvertApi(String baseUrl) {
        apiService = new RetrofitCreateHelper.Builder<ApiService>().baseUrl(baseUrl).addConverterFactory(new ToStringConverterFactory()).build(ApiService.class);
        return apiService;
    }
}
