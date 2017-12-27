package com.pro.feng.hf.contact.presenter.movie;


import android.widget.ImageView;

import com.pro.feng.hf.bean.HotMovieBean;
import com.pro.feng.hf.bean.SubjectsBean;
import com.pro.feng.hf.contact.interfaces.movie.MovieContact;
import com.pro.feng.hf.contact.model.movie.MovieModel;
import com.pro.feng.hf.core.http.okhttp.callback.HFNetConsumer;
import com.pro.feng.hf.core.mvpbase.presenter.BaseMvpPresenter;

/**
 * Created by Feng on 2017/12/19.
 */

public class MoviePresenter extends BaseMvpPresenter<MovieContact> {
    private MovieModel model;

    public MoviePresenter() {
        model = new MovieModel();
    }

    public void loadListData(){

        model.requestData(new HFNetConsumer<HotMovieBean>() {

            @Override
            public void accept(HotMovieBean hotMovieBean) throws Exception {
                getPresenterView().loadDataSuccess(hotMovieBean.getSubjects());
            }

            @Override
            public void error(Throwable throwable) {
                getPresenterView().showNetworkError();
            }
        });
    }

    @Override
    public void onDestroyPresenter() {
        super.onDestroyPresenter();
        model.release();
    }
}
