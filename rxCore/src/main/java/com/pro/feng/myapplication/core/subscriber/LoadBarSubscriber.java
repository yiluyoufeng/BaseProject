package com.pro.feng.myapplication.core.subscriber;

import android.app.Activity;
import android.content.Context;

import com.pro.feng.myapplication.core.loading.JYLoadingDialog;
import com.pro.feng.myapplication.core.okhttp.callback.JYNetListener;

import java.lang.ref.SoftReference;

/**
 * 用于在Http请求开始时，自动显示一个加载Dialog
 * 在Http请求结束是，关闭ProgressDialog
 */

public class LoadBarSubscriber<T> extends ResultAuthSubscriber<T> {

    private boolean isCancel = true;
    /*加载框可自己定义*/
    private JYLoadingDialog pd;
    /*软引用反正内存泄露*/
    private SoftReference<Activity> mActivity;

    public LoadBarSubscriber(boolean isCancel, Context activity, JYNetListener<T> listener){
        this.isCancel = isCancel;
        mActivity = new SoftReference(activity) ;
        initProgressDialog();
        netListener = listener;
    }

    /**
     * 初始化加载框
     */
    private void initProgressDialog() {
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = new JYLoadingDialog(context);
            pd.setCancelable(isCancel);
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
        if(netListener!=null){
            netListener.onCompleted();
        }
    }

    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        Context context = mActivity.get();
        if (pd == null || context == null) return;
        if (!pd.isShowing()) {
            pd.show();
        }
    }

    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if (pd != null && pd.isShowing()) {
            try{
                pd.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissProgressDialog();
    }
}
