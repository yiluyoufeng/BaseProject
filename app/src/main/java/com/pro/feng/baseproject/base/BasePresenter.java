package com.pro.feng.baseproject.base;

/**
 * Created by Feng on 2017/12/12.
 */

public abstract class BasePresenter<V extends BaseInterface> {
    private V view;

    public void attach(V view){
        this.view = view;
    }

    public void detch(){
        view = null;
    }

    public V getPresenterView(){
        return view;
    }
}
