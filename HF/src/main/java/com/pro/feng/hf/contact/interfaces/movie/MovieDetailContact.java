package com.pro.feng.hf.contact.interfaces.movie;

import com.pro.feng.hf.bean.MovieDetailBean;
import com.pro.feng.hf.core.mvpbase.view.BaseMvpView;

/**
 * Created by Feng on 2017/12/21.
 */

public interface MovieDetailContact extends BaseMvpView{

    void loadDetailSuccess(MovieDetailBean bean);

    void loadDetailFail();
}
