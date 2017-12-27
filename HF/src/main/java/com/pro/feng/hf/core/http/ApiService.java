package com.pro.feng.hf.core.http;

import com.pro.feng.hf.bean.HotMovieBean;
import com.pro.feng.hf.bean.MovieDetailBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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
    Observable<String> getGetData(@QueryMap Map<String, String> map);

    /**
     * 请求天气接口
     * @param cityId 城市
     * @return Call
     */
    @GET("data/cityinfo/{cityId}.html")
    Observable<String> requestWeather(@Path("cityId") String cityId);

    @GET("/nc/article/headline/T1348647909107/{id}-20.html")
    Observable<String> getNewsList(@Path("id") int id);

    /**
     * 豆瓣热映电影，每日更新
     */
    @GET("v2/movie/in_theaters")
    Observable<HotMovieBean> getHotMovie();

    /**
     * 获取电影详情
     *
     * @param id 电影bean里的id
     */
    @GET("v2/movie/subject/{id}")
    Observable<MovieDetailBean> getMovieDetail(@Path("id") String id);
}
