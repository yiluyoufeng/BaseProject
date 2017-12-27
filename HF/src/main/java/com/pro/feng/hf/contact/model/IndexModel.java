package com.pro.feng.hf.contact.model;

import com.pro.feng.hf.base.RxManager;
import com.pro.feng.hf.core.http.ApiManager;
import com.pro.feng.hf.core.http.NetHelperUtil;
import com.pro.feng.hf.core.http.okhttp.callback.HFNetConsumer;

/**
 * Created by Feng on 2017/12/20.
 */

public class IndexModel {
    String baseUrl = "http://c.m.163.com";

    RxManager rxManager = new RxManager();
    public void requestData(int id, HFNetConsumer<String> callback) {
        rxManager.register(NetHelperUtil.postToBeanBack(ApiManager.getConvertApi(baseUrl).getNewsList(id), callback));
    }

    public void release(){
        rxManager.unSubscribe();
    }
}
