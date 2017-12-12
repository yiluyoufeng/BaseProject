package com.pro.feng.baseproject.contact2;

import com.pro.feng.baseproject.api.WeatherBean;
import com.pro.feng.baseproject.base.BaseInterface;

/**
 * Created by Feng on 2017/12/12.
 */

public interface Request2View extends BaseInterface{

    void requestLoading();

    void requestResult(WeatherBean bean);

    void requestFailure(String failure);
}
