package com.pro.feng.hf.contact.presenter.index;

import com.pro.feng.hf.contact.interfaces.index.IndexContact;
import com.pro.feng.hf.core.http.okhttp.callback.HFNetConsumer;
import com.pro.feng.hf.core.http.okhttp.callback.HFNetListener;
import com.pro.feng.hf.core.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.hf.contact.model.IndexModel;
import com.pro.feng.hf.utils.LogUtils;

/**
 * Created by Feng on 2017/12/19.
 */

public class IndexPresenter extends BaseMvpPresenter<IndexContact>{
    IndexModel model;
    private int mCurrentIndex;
    boolean isLoading = false;

    public IndexPresenter() {
        model = new IndexModel();
    }

    public void loadListData(int id){
        mCurrentIndex = id;
        model.requestData(id, new HFNetConsumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mCurrentIndex += 20;
                getPresenterView().loadDataSuccess(s);
            }

            @Override
            public void error(Throwable throwable) {
                getPresenterView().showNetworkError();
            }
        });
    }

    public void loadMoreList() {
        if (!isLoading) {
            isLoading = true;
            model.requestData(mCurrentIndex, new HFNetConsumer<String>() {
                @Override
                public void accept(String entity) throws Exception {
                    isLoading = false;
                    LogUtils.e(entity);
                    mCurrentIndex += 20;
                    getPresenterView().loadDataSuccess(entity);
                }

                @Override
                public void error(Throwable e) {
                    isLoading = false;
                    LogUtils.e("------------网络异常--------");
                    getPresenterView().loadMoreError();
                }
            });
        }
    }

    @Override
    public void onDestroyPresenter() {
        super.onDestroyPresenter();
        model.release();
    }
}
