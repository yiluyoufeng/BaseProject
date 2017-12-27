package com.pro.feng.hf.core.mvpbase.presenter;

import android.os.Bundle;

import com.pro.feng.hf.core.mvpbase.view.BaseMvpView;


/**
 * Created by Feng on 2017/12/12.
 * presenter 负责连接V层与M层的纽带，因此，在此类中持有Model的实例，和View的实例
 * 在基类中，为了解决View层引起的内存泄漏，封装了View的基本操作
 */

public class BaseMvpPresenter<V extends BaseMvpView> {
    private V view;

    /**
     * 创建
     *
     * @param bundle
     */
    public void onCreatePresenter(Bundle bundle) {

    }

    /**
     * 绑定 View
     *
     * @param view
     */
    public void onAttachMvpView(V view) {
        this.view = view;
    }

    /**
     * Presenter 意外销毁时调用
     *
     * @param bundle
     */
    public void onSaveInstanceState(Bundle bundle) {

    }

    /**
     * 解绑View
     */
    public void onDetachMvpView() {
        this.view = null;
    }

    /**
     * 销毁onDestroy
     */
    public void onDestroyPresenter() {

    }


    public V getPresenterView() {
        return view;
    }
}
