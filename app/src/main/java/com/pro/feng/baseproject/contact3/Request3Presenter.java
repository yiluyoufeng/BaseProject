package com.pro.feng.baseproject.contact3;

import com.pro.feng.baseproject.api.WeatherBean;
import com.pro.feng.baseproject.model.WeatherModel;
import com.pro.feng.baseproject.mvpbase.factory.CreatePresenter;
import com.pro.feng.baseproject.mvpbase.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Feng on 2017/12/12.
 */
public class Request3Presenter extends BaseMvpPresenter<Request3View>{

    private WeatherModel model;

    //P 层负责连接M层与V层，M层与V层完全解耦

    /***
     *  在这个实例中RequestPresenter(RequestInterface view)，P层持有V层（Activity等）的实例，当关闭应用，而网络没有请求完成，就会造成内存泄漏
     *  通过绑定、解绑方法，防止内存泄漏，绑定View的生命周期，在View销毁时，释放引用，断开网络连接取消请求
     *  */
    public Request3Presenter() {
        model = new WeatherModel();
    }

    public void requestData(String city) {
        if (getPresenterView() == null) {
            return;
        }
        getPresenterView().requestLoading();
        model.requestData(city, new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                getPresenterView().requestResult(response.body());
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {
                getPresenterView().requestFailure("请求失败！！！");
            }
        });
    }


    public void interruptHttp() {
        model.interruptHttp();
    }
}
