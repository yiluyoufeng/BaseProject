package com.pro.feng.baseproject.mvpbase.proxy;

import com.pro.feng.baseproject.mvpbase.factory.PresenterMvpFactory;
import com.pro.feng.baseproject.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.baseproject.mvpbase.view.BaseMvpView;

/**
 * Created by Feng on 2017/12/12.
 */

public interface PresenterProxyInterface<V extends BaseMvpView,P extends BaseMvpPresenter<V>> {

    void setPresenterFactory(PresenterMvpFactory<V,P> factory);

    PresenterMvpFactory<V,P> getPresenterFactory();

    P getMvpPresenter();
}
