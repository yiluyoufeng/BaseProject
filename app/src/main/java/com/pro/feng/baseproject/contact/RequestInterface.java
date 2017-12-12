package com.pro.feng.baseproject.contact;

import com.pro.feng.baseproject.api.WeatherBean;

/**
 * Created by Feng on 2017/12/12.
 */

public interface RequestInterface {
    void requestLoading();

    void requestResult(WeatherBean bean);

    void requestFailure(String failure);
}
