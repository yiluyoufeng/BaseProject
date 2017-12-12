package com.pro.feng.baseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.pro.feng.baseproject.api.ApiService;
import com.pro.feng.baseproject.api.WeatherBean;
import com.pro.feng.baseproject.contact.RequestInterface;
import com.pro.feng.baseproject.contact.RequestPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements RequestInterface{
    TextView tvContent;
    Button btnRequest;
    RequestPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = findViewById(R.id.tv_request);
        btnRequest = findViewById(R.id.btn_request);
//        presenter = new RequestPresenter(this);
        presenter = new RequestPresenter();
        presenter.attachView(this);
        btnRequest.setOnClickListener(v->{
//            requestData("101010100");
//            presenter.requestData("101010100");
            Intent intent = new Intent(this,Main2Activity.class);
            startActivity(intent);
        });

    }

//    private void requestData(String city) {
//        Retrofit retrofit = new Retrofit.Builder()
//                //代表root地址
//                .baseUrl("http://www.weather.com.cn/")
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiService apiService = retrofit.create(ApiService.class);
//        //请求
//        Call<WeatherBean> weatherBeanCall = apiService.requestWeather(city);
//        weatherBeanCall.enqueue(new Callback<WeatherBean>() {
//            @Override
//            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
//                //请求成功
//                tvContent.setText(response.body().getWeatherinfo().toString());
//            }
//            @Override
//            public void onFailure(Call<WeatherBean> call, Throwable t) {
//                //请求失败
//            }
//        });
//    }

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
        presenter.interruptHttp();
        presenter.detach();
        super.onDestroy();
    }
}
