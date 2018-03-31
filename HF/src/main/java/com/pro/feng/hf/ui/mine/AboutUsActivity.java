package com.pro.feng.hf.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.pro.feng.hf.R;
import com.pro.feng.hf.utils.LogUtils;
import com.pro.feng.hf.widget.view.MyView;

/**
 * Created by Feng on 2017/12/23.
 */

public class AboutUsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogUtils.e("view------------onClick");
//            }
//        });
//
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                LogUtils.e("view------------onTouch");
//                return true;
//            }
//        });
    }

}
