package com.pro.feng.hf.core.mvpbase.factory;


import com.pro.feng.hf.core.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.hf.core.mvpbase.view.BaseMvpView;

/**
 * Created by Feng on 2017/12/12.
 * 工厂实现类，通过条件，穿件不同的Presenter
 */

public class PresenterMvpFactoryImp<V extends BaseMvpView,P extends BaseMvpPresenter<V>> implements PresenterMvpFactory<V,P>{
    private final Class<P> mPresenterClass;//反射

    public PresenterMvpFactoryImp(Class<P> mPresenterClass) {
        this.mPresenterClass = mPresenterClass;
    }

    public static <V extends BaseMvpView,P extends BaseMvpPresenter<V>>  PresenterMvpFactoryImp<V,P> createFactory(Class<?> viewClazz){
        CreatePresenter annotation = viewClazz.getAnnotation(CreatePresenter.class);
        Class<P> clazz = null;
        if(annotation != null){
            clazz = (Class<P>) annotation.value();
        }
        return clazz == null ? null:new PresenterMvpFactoryImp<>(clazz);
    }

    @Override
    public P createPresenter() {
        try {
            return mPresenterClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Presenter创建失败!，检查是否声明了@CreatePresenter(xx.class)注解");
        }
    }
}
