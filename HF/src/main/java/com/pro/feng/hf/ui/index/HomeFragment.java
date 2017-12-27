package com.pro.feng.hf.ui.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pro.feng.hf.R;
import com.pro.feng.hf.adapter.HomeAdapter;
import com.pro.feng.hf.base.BaseRecycleFragment;
import com.pro.feng.hf.bean.WangyiNewsItemBean;
import com.pro.feng.hf.contact.interfaces.index.IndexContact;
import com.pro.feng.hf.core.mvpbase.factory.CreatePresenter;
import com.pro.feng.hf.contact.presenter.index.IndexPresenter;
import com.pro.feng.hf.utils.GsonUtil;
import com.pro.feng.hf.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2017/12/18.
 */
@CreatePresenter(IndexPresenter.class)
public class HomeFragment extends BaseRecycleFragment<IndexContact,IndexPresenter> implements IndexContact,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    RecyclerView recycle;
    SwipeRefreshLayout mSwipe;
    private HomeAdapter mAdapter;
    private List<WangyiNewsItemBean> list;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    protected void initView(View view, Bundle saveInstanceState) {
        mSwipe = view.findViewById(R.id.swipeLayout);
        recycle = view.findViewById(R.id.rv_list);

        list = new ArrayList<>();
        recycle.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new HomeAdapter(R.layout.item_recycle_home,list);
        mAdapter.setOnLoadMoreListener(this,recycle);
        recycle.setAdapter(mAdapter);

        mSwipe.setOnRefreshListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getMvpPresenter().loadListData(0);
    }

    @Override
    protected void onErrorViewClick(View view) {
        getMvpPresenter().loadListData(0);
    }

    @Override
    protected void showLoading() {
        mAdapter.setEmptyView(loadingView);
    }

    @Override
    public void loadDataSuccess(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray array = jsonObject.optJSONArray("T1348647909107");
            list.clear();
            list.addAll(GsonUtil.jsonToArrayList(array.toString(),WangyiNewsItemBean.class));
            if(list.size() == 0){
                mAdapter.setEmptyView(emptyView);
            }
            LogUtils.e(list.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter.setNewData(list);
        mAdapter.loadMoreComplete();
        mSwipe.setRefreshing(false);
    }

    @Override
    public void showTopLoading() {
        mSwipe.setRefreshing(true);
    }

    @Override
    public void loadMoreError() {
        mSwipe.setRefreshing(false);
        mAdapter.loadMoreFail();
    }

    @Override
    public void showNetworkError() {
        mSwipe.setRefreshing(false);
        mAdapter.setEmptyView(errorView);
    }

    @Override
    public void onRefresh() {
        getMvpPresenter().loadListData(0);
    }

    @Override
    public void onLoadMoreRequested() {
        LogUtils.e("--------onLoadMoreRequested--------");
        getMvpPresenter().loadMoreList();
    }
}
