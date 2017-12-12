package com.pro.feng.baseproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.pro.feng.baseproject.api.WeatherBean;
import com.pro.feng.baseproject.base.BaseActivity;
import com.pro.feng.baseproject.contact2.Request2Presenter;
import com.pro.feng.baseproject.contact2.Request2View;

/**
 * Created by Feng on 2017/12/12.
 */

public class Main2Activity extends BaseActivity<Request2View,Request2Presenter> implements Request2View {

    TextView tvContent;
    Button btnRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = findViewById(R.id.tv_request);
        btnRequest = findViewById(R.id.btn_request);

        btnRequest.setOnClickListener(v->{
            getPresenter().requestData("101010100");
        });
    }

    @Override
    protected Request2Presenter createPresenter() {
        return new Request2Presenter();
    }


    @Override
    public void requestLoading() {
        tvContent.setText("请稍后...");
    }

    @Override
    public void requestResult(WeatherBean bean) {
        tvContent.setText(bean.getWeatherinfo().toString());
    }

    @Override
    public void requestFailure(String failure) {
        tvContent.setText(failure);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().interruptHttp();
    }
}
