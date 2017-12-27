package com.pro.feng.hf.contact.model.movie;

import com.pro.feng.hf.bean.MovieDetailBean;
import com.pro.feng.hf.core.http.ApiManager;
import com.pro.feng.hf.core.http.NetHelperUtil;
import com.pro.feng.hf.core.http.okhttp.callback.HFNetListener;

/**
 * Created by Feng on 2017/12/21.
 */

public class MovieDetailModel {

    String baseUrl = "Https://api.douban.com/";

    public void requestData(String movieId,HFNetListener<MovieDetailBean> callback) {
        NetHelperUtil.postToBean(ApiManager.getApi(baseUrl).getMovieDetail(movieId), callback);
    }
}
