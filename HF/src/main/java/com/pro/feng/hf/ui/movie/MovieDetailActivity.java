package com.pro.feng.hf.ui.movie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pro.feng.hf.R;
import com.pro.feng.hf.adapter.MovieDetailAdapter;
import com.pro.feng.hf.base.BaseActivity;
import com.pro.feng.hf.bean.MovieDetailBean;
import com.pro.feng.hf.bean.PersonBean;
import com.pro.feng.hf.bean.SubjectsBean;
import com.pro.feng.hf.contact.interfaces.movie.MovieDetailContact;
import com.pro.feng.hf.core.mvpbase.factory.CreatePresenter;
import com.pro.feng.hf.contact.presenter.movie.MovieDetailPresenter;
import com.pro.feng.hf.utils.DisplayUtils;
import com.pro.feng.hf.utils.StatusBarUtils;
import com.pro.feng.hf.widget.view.CompatNestedScrollView;

import java.util.List;


/**
 * Created by Feng on 2017/12/21.
 */
@CreatePresenter(MovieDetailPresenter.class)
public class MovieDetailActivity extends BaseActivity<MovieDetailContact, MovieDetailPresenter> implements MovieDetailContact {
    public static final String INTENT_KEY_MOVIE_SUBJECTBEAN = "intent_key_movie_subjectbean";
    Toolbar toolbar;
    ImageView ivHeaderBg;
    ImageView ivMoviePhoto;
    TextView tvMovieRatingRate;
    TextView tvMovieRatingNumber;
    TextView tvCollectCount;
    TextView tvMovieDirectors;
    TextView tvMovieCasts;
    TextView tvMovieGenres;
    TextView tvMovieDate;
    TextView tvMovieCity;
    RecyclerView rvMovieDetail;
    ImageView ivToolbarBg;
    CompatNestedScrollView nsvScrollView;
    TextView tvMovieSubTitle;
    TextView tvMovieSummary;

    private SubjectsBean mSubjectBean;
    private MovieDetailAdapter mMovieDetailAdapter;
    private View errorView;

    public static void startToActivity(Activity context, SubjectsBean subjectsBean, ImageView imageView) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(INTENT_KEY_MOVIE_SUBJECTBEAN, subjectsBean);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (context, imageView, context.getResources().getString(R.string.transition_movie_img));
        //与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_movie_detail_layout);
        toolbar = findViewById(R.id.tb_toolbar);
        ivHeaderBg = findViewById(R.id.iv_header_bg);
        ivMoviePhoto = findViewById(R.id.iv_movie_photo);
        tvMovieRatingRate = findViewById(R.id.tv_movie_rating_rate);
        tvMovieRatingNumber = findViewById(R.id.tv_movie_rating_number);
        tvMovieDirectors = findViewById(R.id.tv_movie_directors);
        tvCollectCount = findViewById(R.id.tv_collect_count);
        tvMovieCasts = findViewById(R.id.tv_movie_casts);
        tvMovieGenres = findViewById(R.id.tv_movie_genres);
        tvMovieDate = findViewById(R.id.tv_movie_date);
        tvMovieCity = findViewById(R.id.tv_movie_city);
        rvMovieDetail = findViewById(R.id.rv_movie_detail);
        ivToolbarBg = findViewById(R.id.iv_toolbar_bg);
        nsvScrollView = findViewById(R.id.nsv_scrollview);
        tvMovieSubTitle = findViewById(R.id.tv_movie_sub_title);
        tvMovieSummary = findViewById(R.id.tv_moive_summary);
        if (getIntent() != null) {
            mSubjectBean = (SubjectsBean) getIntent().getSerializableExtra
                    (INTENT_KEY_MOVIE_SUBJECTBEAN);
        }

        initToolBar(mSubjectBean.getTitle());
        initHeaderView(mSubjectBean);

        mMovieDetailAdapter = new MovieDetailAdapter(R.layout
                .item_movie_detail_person);
        rvMovieDetail.setAdapter(mMovieDetailAdapter);
        rvMovieDetail.setLayoutManager(new LinearLayoutManager(this));
        rvMovieDetail.setNestedScrollingEnabled(false);
        nsvScrollView.bindAlphaView(ivToolbarBg);
        getMvpPresenter().loadMovieDetail(mSubjectBean.getId());

        errorView = getLayoutInflater().inflate(R.layout.view_network_error, rvMovieDetail, false);
        errorView.setOnClickListener((View v) -> {
            getMvpPresenter().loadMovieDetail(mSubjectBean.getId());
        });
    }


    private void initHeaderView(SubjectsBean subjectsBean) {
        tvMovieRatingNumber.setText(String.valueOf(subjectsBean.getRating().getAverage()));
        tvCollectCount.setText(String.valueOf(subjectsBean.getCollect_count()));
        tvMovieDirectors.setText(subjectsBean.getDirectorsString());
        tvMovieCasts.setText(subjectsBean.getActorsString());
        tvMovieGenres.setText(subjectsBean.getGenresString());
        tvMovieDate.setText(subjectsBean.getYear());
        Glide.with(this).load(subjectsBean.getImages().getLarge()).asBitmap().into(ivMoviePhoto);
        DisplayUtils.displayBlurImg(this, subjectsBean.getImages().getLarge(), ivHeaderBg);
        DisplayUtils.displayBlurImg(this, subjectsBean.getImages().getLarge(), ivToolbarBg);

        int headerBgHeight = toolbar.getLayoutParams().height + StatusBarUtils.getStatusBarHeight(this);
        // 使背景图向上移动到图片的最低端，保留（toolbar+状态栏）的高度
        // 实际上此时ivToolbarBg高度还是330dp，只是除了toolbar外，剩下部分是透明状态
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams)
                ivToolbarBg.getLayoutParams();
        int marginTop = ivToolbarBg.getLayoutParams().height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);
    }

    @Override
    public void loadDetailSuccess(MovieDetailBean bean) {
        if (mMovieDetailAdapter.getData().size() == 0) {
            initRecycleView(bean);
            tvMovieCity.setText("制片国家/地区： " + bean.getCountriesString());
            tvMovieSubTitle.setText(bean.getAkaString());
            tvMovieSummary.setText(bean.getSummary());
        } else {
            mMovieDetailAdapter.addData(bean.getCasts());
        }
    }

    @Override
    public void loadDetailFail() {
        mMovieDetailAdapter.setEmptyView(errorView);
    }

    private void initRecycleView(MovieDetailBean bean) {
        List<PersonBean> list = bean.getDirectors();
        list.addAll(bean.getCasts());
        mMovieDetailAdapter = new MovieDetailAdapter(R.layout
                .item_movie_detail_person, list);
        rvMovieDetail.setAdapter(mMovieDetailAdapter);
    }
}
