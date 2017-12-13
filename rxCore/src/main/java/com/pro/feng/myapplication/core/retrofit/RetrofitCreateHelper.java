package com.pro.feng.myapplication.core.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pro.feng.myapplication.core.okhttp.HttpCache;
import com.pro.feng.myapplication.core.okhttp.interceptor.AppInterceptor;
import com.pro.feng.myapplication.core.okhttp.interceptor.NetworkInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Feng on 2017/12/13.
 * 创建Retrofit，配置基本参数，提供创建Api的方法
 */

public class RetrofitCreateHelper {

    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();
    int connectTimeout; //连接超时时间
    int readTimeout; //读取超时时间
    int writeTimeout; //写入超时时间
    Interceptor networkInterceptor; //网络拦截器
    Interceptor loggingInterceptor; // 日志拦截器
    Interceptor appInterceptor; // 日志拦截器
    boolean retryOnConnectionFailure;//自动重连
    Cache cache; //缓存
    String baseUrl; //主机ip
    CallAdapter.Factory callAdapterfactory;
    Converter.Factory converterfactory;
    private OkHttpClient client;
    private Retrofit retrofit;

    public RetrofitCreateHelper(Builder builder) {
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.networkInterceptor = builder.networkInterceptor;
        this.loggingInterceptor = builder.loggingInterceptor;
        this.appInterceptor = builder.appInterceptor;
        this.retryOnConnectionFailure = builder.retryOnConnectionFailure;
        this.cache = builder.cache;
        this.baseUrl = builder.baseUrl;
        this.callAdapterfactory = builder.callAdapterfactory;
        this.converterfactory = builder.converterfactory;

    }

    public <T> T createApi(Class<T> clazz) {
        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(retryOnConnectionFailure)//断开重连
                .readTimeout(readTimeout, TimeUnit.SECONDS)//读取时间
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)//连接时间
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)//写入时间
                .addInterceptor(loggingInterceptor)//日志拦截器
                .addInterceptor(appInterceptor)
                .addNetworkInterceptor(networkInterceptor)//网络拦截
                .cache(cache)//缓存
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(callAdapterfactory)//增加 RxJava 适配器
                .addConverterFactory(converterfactory)
                .build();
        return retrofit.create(clazz);
    }

    public static class Builder<T>{
        int connectTimeout = 10; //连接超时时间
        int readTimeout = 20; //读取超时时间
        int writeTimeout = 20; //写入超时时间
        Interceptor networkInterceptor; //网络拦截器
        Interceptor loggingInterceptor; // 日志拦截器
        Interceptor appInterceptor; // 日志拦截器
        boolean retryOnConnectionFailure;//自动重连
        Cache cache; //缓存
        String baseUrl; //主机ip
        CallAdapter.Factory callAdapterfactory;
        Converter.Factory converterfactory;

        public Builder() {//设置默认参数
            connectTimeout = 10;
            readTimeout = 20;
            writeTimeout = 20;
            networkInterceptor = new NetworkInterceptor.Builder().build();
            appInterceptor = new AppInterceptor.Builder()//TODO 添加固定参数
                    .addParam("appVersion","")
                    .addParam("apiVersion", "")
                    .addParam("systemVersion", "")
                    .addParam("systemType", "1")
                    .addParam("phoneType", "")
                    .addParam("deviceToken","")
                    .addParam("androidType", "") //安卓机类型:0华为 1小米 2其他 (目前华为推送无法使用所以固定用1)
                    .build();

            loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            retryOnConnectionFailure = true;
            cache = HttpCache.getCache();
            baseUrl = "";//TODO 添加baseUrl
            callAdapterfactory = RxJavaCallAdapterFactory.create();
            converterfactory = GsonConverterFactory.create(gson);
        }

        public T build(Class<T> api){
            return new RetrofitCreateHelper(this).createApi(api);
        }

        public Builder<T> retryOnConnectionFailure(boolean retryOnConnectionFailure) {
            this.retryOnConnectionFailure = retryOnConnectionFailure;
            return this;
        }
        public Builder<T> readTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder<T> connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder<T> writeTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder<T> addAppInterceptor(Interceptor appInterceptor){
            this.appInterceptor = appInterceptor;
            return this;
        }

        public Builder<T> addNetworkInterceptor(Interceptor interceptor) {
            this.networkInterceptor = interceptor;
            return this;
        }

        public Builder<T> cache(Cache cache) {
            this.cache = cache;
            return this;
        }

        public Builder<T> baseUrl(String baseUrl) {
            this.baseUrl = checkNotNull(baseUrl);
            return this;
        }

        public Builder<T> addCallAdapterFactory(CallAdapter.Factory factory) {
            this.callAdapterfactory = checkNotNull(factory);
            return this;
        }

        public Builder<T> addConverterFactory(Converter.Factory factory) {
            this.converterfactory = checkNotNull(factory);
            return this;
        }
    }

}
