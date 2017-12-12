package com.pro.feng.baseproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Feng on 2017/12/12.
 */

public abstract class BaseActivity<V extends BaseInterface, P extends BasePresenter<V>> extends AppCompatActivity implements BaseInterface{

    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("presenter不能为空");
        }
        presenter.attach((V) this);
    }

    protected abstract P createPresenter();

    public P getPresenter() {
        return presenter;
    }
}
