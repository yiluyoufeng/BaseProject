package com.pro.feng.hf.contact.presenter.mine;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.pro.feng.hf.contact.interfaces.mine.MineContact;
import com.pro.feng.hf.contact.model.MineModel;
import com.pro.feng.hf.core.mvpbase.presenter.BaseMvpPresenter;
import com.pro.feng.hf.utils.FileUtils;
import com.pro.feng.hf.utils.MD5Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

/**
 * Created by Feng on 2017/12/19.
 */

public class MinePresenter extends BaseMvpPresenter<MineContact>{

    MineModel model;
    File tempFile;
    //请求相机
    public static final int REQUEST_CAMERA = 100;
    //请求相册
    public static final int REQUEST_PHOTO = 101;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    public static final String HEAD_IMAGE_NAME = "yizhi_head_image";

    public MinePresenter() {
        model = new MineModel();
    }



    public void btnCameraClicked(RxPermissions rxPermissions,Context context) {
        tempFile = new File(FileUtils.checkDirPath(Environment.getExternalStorageDirectory()
                .getPath() + "/image/"), MD5Utils.getMD5(HEAD_IMAGE_NAME) + System
                .currentTimeMillis() + ".jpg");
        rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe((permission)->{
            if(permission){//允许
                getPresenterView().gotoSystemCamera(tempFile, REQUEST_CAMERA);
            }else {//勾选了不再询问，且拒绝
                Toast.makeText(context,"授权失败",Toast.LENGTH_SHORT).show();
            }
        });
        if (getPresenterView().popupIsShowing())
            getPresenterView().dismissPopupView();
    }

    public void btnPhotoClicked(RxPermissions rxPermissions,Context context) {
        //权限判断
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe((grant)->{
            if(grant){
                getPresenterView().gotoSystemPhoto(REQUEST_PHOTO);
            }else{
                Toast.makeText(context,"授权失败",Toast.LENGTH_SHORT).show();
            }
        });

        if (getPresenterView().popupIsShowing())
            getPresenterView().dismissPopupView();
    }

    public void btnCancelClicked() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAMERA: //调用系统相机返回
                if (resultCode == Activity.RESULT_OK) {
                    getPresenterView().gotoHeadSettingActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PHOTO:  //调用系统相册返回
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    getPresenterView().gotoHeadSettingActivity(uri);
                }
                break;
        }
    }
}
