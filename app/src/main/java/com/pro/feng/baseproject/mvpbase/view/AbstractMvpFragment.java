package com.pro.feng.baseproject.mvpbase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.pro.feng.baseproject.mvpbase.factory.PresenterMvpFactory;
import com.pro.feng.baseproject.mvpbase.factory.PresenterMvpFactoryImp;
import com.pro.feng.baseproject.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.baseproject.mvpbase.proxy.BaseMvpProxy;
import com.pro.feng.baseproject.mvpbase.proxy.PresenterProxyInterface;

/**
 * Created by Feng on 2017/12/12.
 */

public  class AbstractMvpFragment<V extends BaseMvpView,P extends BaseMvpPresenter<V>> extends Fragment implements PresenterProxyInterface<V,P>{
    private BaseMvpProxy<V,P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImp.<V,P>createFactory(getClass()));
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mProxy.onResume((V) this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY,mProxy.onSaveInstanceState());
    }

    @Override
    public void onDestroy() {
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
