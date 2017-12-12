package com.pro.feng.baseproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.pro.feng.baseproject.api.WeatherBean;
import com.pro.feng.baseproject.contact3.Request3Presenter;
import com.pro.feng.baseproject.contact3.Request3View;
import com.pro.feng.baseproject.mvpbase.factory.CreatePresenter;
import com.pro.feng.baseproject.mvpbase.view.AbstractMvpAppCompactActivity;

/**
 * Created by Feng on 2017/12/12.
 */

@CreatePresenter(Request3Presenter.class)
public class Main3Activity extends AbstractMvpAppCompactActivity<Request3View,Request3Presenter> implements Request3View {
    TextView tvContent;
    Button btnRequest;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = findViewById(R.id.tv_request);
        btnRequest = findViewById(R.id.btn_request);

        btnRequest.setOnClickListener(v->{
            getMvpPresenter().requestData("101010100");
        });
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

}
