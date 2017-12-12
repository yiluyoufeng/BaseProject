package com.pro.feng.baseproject.mvpbase.factory;

import com.pro.feng.baseproject.mvpbase.presenter.BaseMvpPresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Feng on 2017/12/12.
 * @Documented： 标记注解，用于描述其它类型的注解应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。
 * @Inherited 注解：声明自定义注解，在使用自定义注解时，如果注解在类上面，子类会自动继承此注解
 * @Retention  指Annotation被保留的时间长短，标明注解的生命周期，3种RetentionPolicy取值含义
 * @Target：   标明注解的修饰目标
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePresenter {
    Class<? extends BaseMvpPresenter> value();
}
