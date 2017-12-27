package com.pro.feng.hf.ui.movie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pro.feng.hf.R;
import com.pro.feng.hf.adapter.MovieAdapter;
import com.pro.feng.hf.base.BaseRecycleFragment;
import com.pro.feng.hf.bean.SubjectsBean;
import com.pro.feng.hf.contact.interfaces.movie.MovieContact;
import com.pro.feng.hf.core.mvpbase.factory.CreatePresenter;
import com.pro.feng.hf.contact.presenter.movie.MoviePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2017/12/19.
 */
@CreatePresenter(MoviePresenter.class)
public class MovieFragment extends BaseRecycleFragment<MovieContact, MoviePresenter> implements MovieContact {
    private RecyclerView mRecycle;
    private List<SubjectsBean> list;
    private MovieAdapter mAdapter;
    private View headView;

    public static MovieFragment newInstance() {
        Bundle args = new Bundle();
        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_layout;
    }

    @Override
    protected void initView(View view, Bundle saveInstanceState) {
        mRecycle = view.findViewById(R.id.rv_list);
        mRecycle.setLayoutManager(new LinearLayoutManager(_mActivity));
        list = new ArrayList<>();
        mAdapter = new MovieAdapter(R.layout.item_hot_movie, list);

        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View viewItem, int position) -> {
//            getMvpPresenter().startDetailActivity(position, (SubjectsBean) adapter.getItem(position), viewItem.findViewById(R.id.iv_moive_photo));
            MovieDetailActivity.startToActivity(_mActivity,(SubjectsBean) adapter.getItem(position), viewItem.findViewById(R.id.iv_moive_photo));
        });
        initHeadView();
        mRecycle.setAdapter(mAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getMvpPresenter().loadListData();
    }

    @Override
    protected void onErrorViewClick(View view) {
        getMvpPresenter().loadListData();
    }

    @Override
    protected void showLoading() {
        mAdapter.setEmptyView(loadingView);
    }

    private void initHeadView() {
        if (headView == null) {
            headView = _mActivity.getLayoutInflater().inflate(R.layout.sub_movie_top_header, null);
        }
        headView.findViewById(R.id.ll_movie_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void loadDataSuccess(List<SubjectsBean> data) {
        mAdapter.removeAllHeaderView();
        mAdapter.addHeaderView(headView);
        list.clear();
        list.addAll(data);
        if (list.size() == 0) {
            mAdapter.setEmptyView(emptyView);
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void showNetworkError() {
        mAdapter.setEmptyView(errorView);
    }
}
