package com.pro.feng.hf.base;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pro.feng.hf.R;
import com.pro.feng.hf.core.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.hf.core.mvpbase.view.BaseAppCompactActivity;
import com.pro.feng.hf.core.mvpbase.view.BaseMvpView;

/**
 * Created by Feng on 2017/12/18.
 */

public abstract class BaseActivity<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends BaseAppCompactActivity<V, P> implements BaseMvpView {

    private Toolbar bar;
    private TextView tvOptionText;
    private ImageButton ivOptionIcon;



    protected void onBackDown() {
        onBackPressedSupport();
    }

    /* 设置标题 */
    protected void setTitleName(String name) {
        if (bar != null) {
            bar.setTitle(name);
        }
    }

    /* 设置标题 */
    protected String getTitleName() {
        if (bar != null) {
            return (String) bar.getTitle();
        } else {
            return "";
        }
    }

    protected void initToolBar(int resId) {
        bar = findViewById(R.id.tb_toolbar);
        bar.setTitle(getResources().getString(resId));
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        bar.setNavigationOnClickListener((v) -> {
            onBackDown();
        });
    }

    protected void initToolBar(String title) {
        bar = findViewById(R.id.tb_toolbar);
        bar.setTitle(title);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        bar.setNavigationOnClickListener((v) -> {
            onBackDown();
        });
    }

    /** 设置栏菜单选项，暂时只支持设置一个图标和一个文本按钮，如需扩展，类似方法
     * 设置标题栏菜单选项
     */
    protected void setToolbarOption(String text, Integer icon, View.OnClickListener btnClick) {
        if (bar != null) {
            if (!TextUtils.isEmpty(text)) {
                ViewStub vsLeft =  bar.findViewById(R.id.tv_menu_option);
                View view = vsLeft.inflate();
                tvOptionText =  view.findViewById(R.id.tv_menu);
                tvOptionText.setText(text);
                tvOptionText.setOnClickListener(btnClick);
            } else if (icon != null) {
                ViewStub ibFunLeft =  bar.findViewById(R.id.ib_menu_option);
                View backLayout = ibFunLeft.inflate();
                ivOptionIcon =  backLayout.findViewById(R.id.ib_menu);
                ivOptionIcon.setImageResource(icon.intValue());
                ivOptionIcon.setOnClickListener(btnClick);
            }
        }
    }

    /* 设置按钮文字 */
    protected void setOptionText(String text) {
        if (tvOptionText != null) {
            tvOptionText.setText(text);
        }
    }

    protected void setOptionIcon(int res){
        if(ivOptionIcon!=null){
            if(res == -1){
                ivOptionIcon.setVisibility(View.GONE);
                return;
            }
            ivOptionIcon.setImageResource(res);
        }
    }
}
