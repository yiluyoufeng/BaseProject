package com.pro.feng.baseproject.mvpbase.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pro.feng.baseproject.mvpbase.factory.PresenterMvpFactory;
import com.pro.feng.baseproject.mvpbase.factory.PresenterMvpFactoryImp;
import com.pro.feng.baseproject.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.baseproject.mvpbase.proxy.BaseMvpProxy;
import com.pro.feng.baseproject.mvpbase.proxy.PresenterProxyInterface;

/**
 * Created by Feng on 2017/12/12.
 */

public abstract class AbstractMvpActivity<V extends BaseMvpView,P extends BaseMvpPresenter<V>> extends Activity implements PresenterProxyInterface<V,P> {
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    private BaseMvpProxy<V,P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImp.<V,P>createFactory(getClass()));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProxy.onResume((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mProxy.onSaveInstanceState();
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
