package com.pro.feng.hf.core.mvpbase.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.feng.hf.core.mvpbase.factory.PresenterMvpFactory;
import com.pro.feng.hf.core.mvpbase.factory.PresenterMvpFactoryImp;
import com.pro.feng.hf.core.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.hf.core.mvpbase.proxy.BaseMvpProxy;
import com.pro.feng.hf.core.mvpbase.proxy.PresenterProxyInterface;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * Created by Feng on 2017/12/12.
 */

public abstract  class BaseFragment<V extends BaseMvpView,P extends BaseMvpPresenter<V>> extends SupportFragment implements PresenterProxyInterface<V,P> {
    private BaseMvpProxy<V,P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImp.<V,P>createFactory(getClass()));
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view,Bundle saveInstanceState);

    @Override
    public void onResume() {
        super.onResume();
        mProxy.onResume((V) this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
