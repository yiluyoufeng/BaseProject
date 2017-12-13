package com.pro.feng.myapplication.core.okhttp.interceptor;


import com.pro.feng.myapplication.core.okhttp.callback.FileResponseBody;
import com.pro.feng.myapplication.core.okhttp.callback.RetrofitCallBack;
import com.pro.feng.myapplication.core.utils.AppUtils;
import com.pro.feng.myapplication.core.utils.LogUtils;
import com.pro.feng.myapplication.core.utils.NetworkConnectionUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络拦截器
 * Created by Feng on 2017/2/9.
 */

public class NetworkInterceptor<T>  implements Interceptor {
    RetrofitCallBack<T> callback;

    private NetworkInterceptor(){
    }

    private NetworkInterceptor(Builder builder){
        this.callback = builder.getCallback();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!NetworkConnectionUtils.isNetworkConnected(AppUtils.getContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        //固定参数 特殊处理
        Request.Builder requestBuilder = request.newBuilder();
        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        LogUtils.e("sessionId=");
        //TODO 添加可变 固定参数
        httpUrlBuilder.addQueryParameter("sessionId", "");
        requestBuilder.url(httpUrlBuilder.build());

        request = requestBuilder.build();
        Response originalResponse = chain.proceed(request);

        if (callback != null){//文件下载，相应回调
            originalResponse = originalResponse.newBuilder().body(new FileResponseBody<>(originalResponse.body(), callback)).build();
        }

        if (NetworkConnectionUtils.isNetworkConnected(AppUtils.getContext())) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")//缓存
                    .removeHeader("Pragma")
                    .build();
        }
    }
    public static class Builder<T> {
        RetrofitCallBack<T> callback;

        public Builder() {
        }

        public RetrofitCallBack<T> getCallback() {
            return callback;
        }

        public Builder setCallback(RetrofitCallBack<T> callback) {
            this.callback = callback;
            return this;
        }

        public NetworkInterceptor build() {
                return new NetworkInterceptor(this);
        }
    }
}
