package com.pro.feng.hf.contact.model;

import com.pro.feng.hf.core.http.ApiManager;
import com.pro.feng.hf.core.http.NetHelperUtil;
import com.pro.feng.hf.core.http.okhttp.callback.HFNetConsumer;

/**
 * Created by Feng on 2017/12/19.
 */

public class MineModel {
    String baseUrl = "http://www.weather.com.cn/";

    public void requestData(String cityId, HFNetConsumer<String> callback) {
        NetHelperUtil.postToBeanBack(ApiManager.getConvertApi(baseUrl).requestWeather(cityId), callback);
    }
}
