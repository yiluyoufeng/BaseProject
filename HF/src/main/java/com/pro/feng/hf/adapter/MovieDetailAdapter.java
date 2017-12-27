package com.pro.feng.hf.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pro.feng.hf.R;
import com.pro.feng.hf.bean.PersonBean;

import java.util.List;

/**
 * Created by Feng on 2017/12/21.
 */

public class MovieDetailAdapter  extends BaseCompatAdapter<PersonBean, BaseViewHolder> {
    public MovieDetailAdapter(@LayoutRes int layoutResId, @Nullable List<PersonBean> data) {
        super(layoutResId, data);
    }

    public MovieDetailAdapter(@Nullable List<PersonBean> data) {
        super(data);
    }

    public MovieDetailAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonBean item) {
        helper.setText(R.id.tv_person_name, item.getName());
        helper.setText(R.id.tv_person_type, item.getType());
        Glide.with(mContext).load(item.getAvatars().getLarge()).crossFade().into((ImageView)
                helper.getView(R.id.iv_avatar_photo));
    }
}
