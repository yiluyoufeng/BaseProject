package com.pro.feng.hf.contact.interfaces.movie;

import com.pro.feng.hf.bean.SubjectsBean;
import com.pro.feng.hf.core.mvpbase.view.BaseMvpView;

import java.util.List;

/**
 * Created by Feng on 2017/12/19.
 */

public interface MovieContact extends BaseMvpView{

    void loadDataSuccess(List<SubjectsBean> data);


    void showNetworkError();

}
