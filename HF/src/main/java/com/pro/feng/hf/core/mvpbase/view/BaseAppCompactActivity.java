package com.pro.feng.hf.core.mvpbase.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pro.feng.hf.core.mvpbase.factory.PresenterMvpFactory;
import com.pro.feng.hf.core.mvpbase.factory.PresenterMvpFactoryImp;
import com.pro.feng.hf.core.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.hf.core.mvpbase.proxy.BaseMvpProxy;
import com.pro.feng.hf.core.mvpbase.proxy.PresenterProxyInterface;

import me.yokeyword.fragmentation.SupportActivity;


/**
 * Created by Feng on 2017/12/12.
 */

public abstract class BaseAppCompactActivity<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends SupportActivity
        implements PresenterProxyInterface<V, P> {

    private BaseMvpProxy<V, P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImp.<V,P>createFactory(getClass()));
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        initView(savedInstanceState);
    }

    protected abstract void initView(Bundle savedInstanceState);

    @Override
    public void onResume() {
        super.onResume();
        mProxy.onResume((V) this);
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY,mProxy.onSaveInstanceState());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }

    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> factory) {
        mProxy.setPresenterFactory(factory);
    }

    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getMvpPresenter() {
        return mProxy.getMvpPresenter();
    }
}
