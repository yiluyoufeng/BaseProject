package com.pro.feng.hf.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pro.feng.hf.R;
import com.pro.feng.hf.bean.WangyiNewsItemBean;

import java.util.List;

/**
 * Created by Feng on 2017/12/20.
 */

public class HomeAdapter extends BaseQuickAdapter<WangyiNewsItemBean,BaseViewHolder>{


    public HomeAdapter(int layoutResId, @Nullable List<WangyiNewsItemBean> data) {
        super(layoutResId, data);
    }

    public HomeAdapter(@Nullable List<WangyiNewsItemBean> data) {
        super(data);
    }

    public HomeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, WangyiNewsItemBean item) {
//        if (DBUtils.getDB(mContext).isRead(DBConfig.TABLE_WANGYI, item.getDocid(), ItemState
//                .STATE_IS_READ)) {
//            helper.setTextColor(R.id.tv_item_title, Color.GRAY);
//        } else {
//            if (SpUtils.getNightModel(mContext)) {
//                helper.setTextColor(R.id.tv_item_title, Color.WHITE);
//            } else {
//
//            }
//        }
        helper.setTextColor(R.id.tv_item_title, Color.BLACK);
        helper.setText(R.id.tv_item_title, item.getTitle());
        helper.setText(R.id.tv_item_who, item.getSource());
        helper.setText(R.id.tv_item_time, item.getPtime());
        Glide.with(mContext).load(item.getImgsrc()).crossFade().into((ImageView) helper.getView(R
                .id.iv_item_image));
    }
}
