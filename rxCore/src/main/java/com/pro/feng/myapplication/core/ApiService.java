package com.pro.feng.myapplication.core;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Feng on 2017/12/13.
 */

public interface ApiService {
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url, @Header("Referer") String header);

    @FormUrlEncoded
    @POST("/customerFeedApi/list")
    Call<String> getPostData(@FieldMap Map<String, String> map);

    @GET("/orderApi/productPageList")
    Call<String> getGetData(@QueryMap Map<String, String> map);
}
