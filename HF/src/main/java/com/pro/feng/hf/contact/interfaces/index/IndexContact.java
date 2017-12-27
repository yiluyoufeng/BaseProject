package com.pro.feng.hf.contact.interfaces.index;

import com.pro.feng.hf.core.mvpbase.view.BaseMvpView;

/**
 * Created by Feng on 2017/12/19.
 */

public interface IndexContact extends BaseMvpView{

    void loadDataSuccess(String data);

    void showTopLoading();

    void loadMoreError();

    void showNetworkError();
}
