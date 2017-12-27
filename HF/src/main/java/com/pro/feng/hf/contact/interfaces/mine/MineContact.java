package com.pro.feng.hf.contact.interfaces.mine;

import android.net.Uri;

import com.pro.feng.hf.core.mvpbase.view.BaseMvpView;

import java.io.File;

/**
 * Created by Feng on 2017/12/19.
 */

public interface MineContact extends BaseMvpView{

    void loadDataSuccess(String data);

    void showPopView();

    void gotoSystemCamera(File tempFile, int requestCamera);

    boolean popupIsShowing();

    void dismissPopupView();

    void gotoHeadSettingActivity(Uri uri);

    void gotoSystemPhoto(int requestPhoto);
}
