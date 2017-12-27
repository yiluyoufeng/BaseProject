package com.pro.feng.hf.core.mvpbase.proxy;

import com.pro.feng.hf.core.mvpbase.factory.PresenterMvpFactory;
import com.pro.feng.hf.core.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.hf.core.mvpbase.view.BaseMvpView;

/**
 * Created by Feng on 2017/12/12.
 */

public interface PresenterProxyInterface<V extends BaseMvpView,P extends BaseMvpPresenter<V>> {

    void setPresenterFactory(PresenterMvpFactory<V, P> factory);

    PresenterMvpFactory<V,P> getPresenterFactory();

    P getMvpPresenter();
}
