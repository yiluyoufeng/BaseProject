package com.pro.feng.hf.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.feng.hf.R;
import com.pro.feng.hf.core.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.hf.core.mvpbase.view.BaseFragment;
import com.pro.feng.hf.core.mvpbase.view.BaseMvpView;

/**
 * Created by Feng on 2017/12/21.
 */

public abstract class BaseRecycleFragment<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends BaseFragment<V, P> {

    /**
     * 网络异常View
     */
    protected View errorView;
    /**
     * loadingView
     */
    protected View loadingView;
    /**
     * 没有内容view
     */
    protected View emptyView;

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        showLoading();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        errorView = inflater.inflate(R.layout.view_network_error, container, false);
        loadingView = inflater.inflate(R.layout.view_loading, container, false);
        emptyView = inflater.inflate(R.layout.view_empty, container, false);
        errorView.setOnClickListener((View v) -> {
            showLoading();
            onErrorViewClick(v);
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 网络异常view被点击时触发，由子类实现
     *
     * @param view view
     */
    protected abstract void onErrorViewClick(View view);

    /**
     * 显示加载中view，由子类实现
     */
    protected abstract void showLoading();
}
