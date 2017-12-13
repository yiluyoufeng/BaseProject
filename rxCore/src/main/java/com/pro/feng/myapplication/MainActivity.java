package com.pro.feng.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pro.feng.myapplication.core.ApiManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<String,String> params = new HashMap<>();
        Call<String> repo = ApiManager.getApi().getGetData(params);
        repo.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
