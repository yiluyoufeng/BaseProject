package com.pro.feng.hf.contact.model.movie;

import com.pro.feng.hf.base.RxManager;
import com.pro.feng.hf.bean.HotMovieBean;
import com.pro.feng.hf.core.http.ApiManager;
import com.pro.feng.hf.core.http.NetHelperUtil;
import com.pro.feng.hf.core.http.okhttp.callback.HFNetConsumer;

/**
 * Created by Feng on 2017/12/21.
 */

public class MovieModel {

    String baseUrl = "Https://api.douban.com/";
    RxManager rxManager = new RxManager();

    public void requestData(HFNetConsumer<HotMovieBean> callback) {
        rxManager.register(NetHelperUtil.postToBeanBack(ApiManager.getApi(baseUrl).getHotMovie(), callback));
    }

    public void release(){
        rxManager.unSubscribe();
    }
}
