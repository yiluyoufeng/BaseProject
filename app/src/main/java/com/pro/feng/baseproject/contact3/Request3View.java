package com.pro.feng.baseproject.contact3;

import com.pro.feng.baseproject.api.WeatherBean;
import com.pro.feng.baseproject.mvpbase.view.BaseMvpView;

/**
 * Created by Feng on 2017/12/12.
 */

public interface Request3View extends BaseMvpView{

    void requestLoading();

    void requestResult(WeatherBean bean);

    void requestFailure(String failure);
}
