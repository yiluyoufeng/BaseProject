package com.pro.feng.hf.core.mvpbase.proxy;

import android.os.Bundle;

import com.pro.feng.hf.core.mvpbase.factory.PresenterMvpFactory;
import com.pro.feng.hf.core.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.hf.core.mvpbase.view.BaseMvpView;


/**
 * Created by Feng on 2017/12/12.
 */

public class BaseMvpProxy<V extends BaseMvpView,P extends BaseMvpPresenter<V>> implements PresenterProxyInterface<V,P> {

    private static final String PRESENTER_KEY = "PRESENTER_KEY";
    private P mPresenter;
    private PresenterMvpFactory<V,P> mFactory;
    private Bundle mBundle;
    private boolean mIsAttachView;

    public BaseMvpProxy(PresenterMvpFactory<V, P> mFactory) {
        this.mFactory = mFactory;
    }

    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> factory) {
        if (mPresenter != null) {
            throw new IllegalArgumentException("这个方法只能在getMvpPresenter()之前调用，如果Presenter已经创建则不能再修改");
        }
        mFactory = factory;
    }

    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mFactory;
    }

    @Override
    public P getMvpPresenter() {
        if(mFactory!=null){
            if(mPresenter == null){
                mPresenter = mFactory.createPresenter();
                mPresenter.onCreatePresenter(mBundle == null? null:mBundle.getBundle(PRESENTER_KEY));
            }
        }
        return mPresenter;
    }


    public void onResume(V view){
        getMvpPresenter();
        if(mPresenter!=null && !mIsAttachView){
            mPresenter.onAttachMvpView(view);
            mIsAttachView = true;
        }
    }

    /**
     * 销毁Presenter持有的View
     */
    private void onDetachMvpView() {
        if (mPresenter != null && mIsAttachView) {
            mPresenter.onDetachMvpView();
            mIsAttachView = false;
        }
    }

    /**
     * 销毁Presenter
     */
    public void onDestroy() {
        if (mPresenter != null ) {
            onDetachMvpView();
            mPresenter.onDestroyPresenter();
            mPresenter = null;
        }
    }

    /**
     * 意外销毁的时候调用
     * @return Bundle，存入回调给Presenter的Bundle和当前Presenter的id
     */
    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        getMvpPresenter();
        if(mPresenter != null){
            Bundle presenterBundle = new Bundle();
            //回调Presenter
            mPresenter.onSaveInstanceState(presenterBundle);
            bundle.putBundle(PRESENTER_KEY,presenterBundle);
        }
        return bundle;
    }

    /**
     * 意外关闭恢复Presenter
     * @param savedInstanceState 意外关闭时存储的Bundler
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mBundle = savedInstanceState;
    }


}
