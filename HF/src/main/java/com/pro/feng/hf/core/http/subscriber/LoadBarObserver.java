package com.pro.feng.hf.core.http.subscriber;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.pro.feng.hf.core.http.loading.HFLoadingDialog;
import com.pro.feng.hf.core.http.okhttp.callback.HFNetListener;
import com.pro.feng.hf.utils.LogUtils;


import java.lang.ref.SoftReference;

import io.reactivex.disposables.Disposable;

/**
 * 用于在Http请求开始时，自动显示一个加载Dialog
 * 在Http请求结束是，关闭ProgressDialog
 */

public class LoadBarObserver<T> extends ResultAuthObserver<T> {

    private boolean isCancel = true;
    /*加载框可自己定义*/
    private HFLoadingDialog pd;
    /*软引用反正内存泄露*/
    private SoftReference<Activity> mActivity;
    private Disposable disposable;

    public LoadBarObserver(boolean isCancel, Context activity, HFNetListener<T> listener) {
        this.isCancel = isCancel;
        mActivity = new SoftReference(activity);
        initProgressDialog();
        netListener = listener;
    }

    /**
     * 初始化加载框
     */
    private void initProgressDialog() {
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = new HFLoadingDialog(context);
            pd.setCancelable(isCancel);
        }

        pd.setOnDismissListener((DialogInterface dialogInterface) -> {
            dispose();
        });
    }


    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        disposable = d;
        showProgressDialog();
    }

    @Override
    public void onComplete() {
        if (netListener != null) {
            netListener.onCompleted();
        }
        dismissProgressDialog();
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
    private void dismissProgressDialog() {//释放
        if (pd != null && pd.isShowing()) {
            try {
                pd.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            LogUtils.e("----------disposable---------");
            disposable.dispose();
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissProgressDialog();
    }
}
