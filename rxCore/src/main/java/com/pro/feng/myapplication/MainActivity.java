package com.pro.feng.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pro.feng.myapplication.core.ApiManager;
import com.pro.feng.myapplication.core.NetHelperUtil;
import com.pro.feng.myapplication.core.okhttp.callback.JYNetListener;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<String, String> params = new HashMap<>();
        NetHelperUtil.postConvert(ApiManager.getApi().getGetData(params), new JYNetListener<String>() {
            @Override
            public void onNetSuccess(String entity) {

            }
        });
    }
}
