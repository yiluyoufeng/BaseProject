package com.pro.feng.hf.core.mvpbase.factory;

import com.pro.feng.hf.core.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.hf.core.mvpbase.view.BaseMvpView;

/**
 * Created by Feng on 2017/12/12.
 * 工厂类，创建不同的Presenter
 *
 */

public interface PresenterMvpFactory<V extends BaseMvpView,P extends BaseMvpPresenter<V>>{

    /**
     * 创建Presenter接口
     * @return
     */
    P createPresenter();
}
