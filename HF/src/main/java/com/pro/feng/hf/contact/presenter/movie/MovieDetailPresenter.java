package com.pro.feng.hf.contact.presenter.movie;

import com.pro.feng.hf.bean.MovieDetailBean;
import com.pro.feng.hf.bean.PersonBean;
import com.pro.feng.hf.contact.interfaces.movie.MovieDetailContact;
import com.pro.feng.hf.core.http.okhttp.callback.HFNetListener;
import com.pro.feng.hf.core.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.hf.contact.model.movie.MovieDetailModel;

/**
 * Created by Feng on 2017/12/21.
 */

public class MovieDetailPresenter extends BaseMvpPresenter<MovieDetailContact> {

    private MovieDetailModel model;

    public MovieDetailPresenter() {
        this.model = new MovieDetailModel();
    }


    public void loadMovieDetail(String id) {
        model.requestData(id, new HFNetListener<MovieDetailBean>() {
            @Override
            public void onNetSuccess(MovieDetailBean entity) {
                for (PersonBean bean1 : entity.getCasts()) {
                    bean1.setType("主演");
                }
                for (PersonBean bean2 : entity.getDirectors()) {
                    bean2.setType("导演");
                }
                getPresenterView().loadDetailSuccess(entity);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (getPresenterView() != null) {
                    getPresenterView().loadDetailFail();
                }
            }
        });
    }
}
