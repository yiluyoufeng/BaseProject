package com.pro.feng.hf.ui.mine;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pro.feng.hf.R;
import com.pro.feng.hf.bean.RxEventHeadBean;
import com.pro.feng.hf.contact.interfaces.mine.MineContact;
import com.pro.feng.hf.contact.presenter.mine.MinePresenter;
import com.pro.feng.hf.core.cache.DataCleanManager;
import com.pro.feng.hf.core.http.loading.HFLoadingDialog;
import com.pro.feng.hf.core.img.GlideCircleTransform;
import com.pro.feng.hf.core.img.ImageLoader;
import com.pro.feng.hf.core.mvpbase.factory.CreatePresenter;
import com.pro.feng.hf.core.mvpbase.view.BaseFragment;
import com.pro.feng.hf.rxbus.RxBus;
import com.pro.feng.hf.rxbus.Subscribe;
import com.pro.feng.hf.utils.AppUtils;
import com.pro.feng.hf.utils.FileUtils;
import com.pro.feng.hf.utils.LogUtils;
import com.pro.feng.hf.widget.view.PersonalPopupWindow;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import static com.pro.feng.hf.utils.FinalDataUtil.RX_BUS_CODE_HEAD_IMAGE_URI;

/**
 * Created by Feng on 2017/12/19.
 */
@CreatePresenter(MinePresenter.class)
public class MineFragment extends BaseFragment<MineContact,MinePresenter> implements MineContact, View.OnClickListener {
    private ImageView ivHead;
    private View linRoot;
    private PersonalPopupWindow popupWindow;
    private TextView tvClear,tvAbout;
    RxPermissions rxPermissions;
    private HFLoadingDialog loadingDialog;

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    protected void initView(View view, Bundle saveInstanceState) {
        ivHead = view.findViewById(R.id.iv_head);
        linRoot = view.findViewById(R.id.lin_root);
        tvClear = view.findViewById(R.id.home_my_tv_clear);
        tvAbout = view.findViewById(R.id.home_my_tv_about);
        initPopupView();
        ivHead.setOnClickListener(v->{
            showPopOption();
        });
        tvClear.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        rxPermissions = new RxPermissions(_mActivity);
        RxBus.get().register(this);
    }

    private void showPopOption() {
        popupWindow.showAtLocation(linRoot, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
    }

    public void initPopupView() {
        popupWindow = new PersonalPopupWindow(_mActivity);
        popupWindow.setOnItemClickListener(new PersonalPopupWindow.OnItemClickListener() {
            @Override
            public void onCaremaClicked() {
                getMvpPresenter().btnCameraClicked(rxPermissions,_mActivity);
            }

            @Override
            public void onPhotoClicked() {
                getMvpPresenter().btnPhotoClicked(rxPermissions,_mActivity);
            }

            @Override
            public void onCancelClicked() {
                getMvpPresenter().btnCancelClicked();
            }
        });
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

    @Override
    public void loadDataSuccess(String data) {
        LogUtils.e("--------"+data);
    }

    @Override
    public void showPopView() {

    }

    @Override
    public void gotoSystemCamera(File tempFile, int requestCamera) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            //            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //            Uri contentUri = FileProvider.getUriForFile(mActivity, BuildConfig.APPLICATION_ID + "" +
            //                    ".fileProvider", tempFile);
            //            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
            Uri uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        LogUtils.e("----------gotoSystemCamera");
        startActivityForResult(intent, requestCamera);
    }

    @Override
    public boolean popupIsShowing() {
        return popupWindow.isShowing();
    }

    @Override
    public void dismissPopupView() {
        popupWindow.dismiss();
    }

    @Override
    public void gotoHeadSettingActivity(Uri uri) {
        if (uri == null) {
            return;
        }

        Intent intent = new Intent();
        intent.setClass(_mActivity, HeadSettingActivity.class);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void gotoSystemPhoto(int requestPhoto) {
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), requestPhoto);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("----------onActivityResult");
        getMvpPresenter().onActivityResult(requestCode, resultCode, data);
    }

    /**
     * RxBus接收图片Uri
     *
     * @param bean RxEventHeadBean
     */
    @Subscribe(code = RX_BUS_CODE_HEAD_IMAGE_URI)
    public void rxBusEvent(RxEventHeadBean bean) {
        Uri uri = bean.getUri();
        if (uri == null) {
            return;
        }
        String cropImagePath = FileUtils.getRealFilePathFromUri(AppUtils.getContext(), uri);
        if (cropImagePath != null)
            Glide.with(getActivity()).load(cropImagePath).transform(new GlideCircleTransform(ivHead.getContext())).into((ivHead));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.home_my_tv_clear:
                if(loadingDialog == null){
                    loadingDialog = new HFLoadingDialog(getActivity());
                }
                loadingDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataCleanManager.cleanExtranalChche(getActivity());//删除外部缓存
                        DataCleanManager.cleanInternalCache(getActivity());//删除内部缓存
                        ImageLoader.clearImageDiskCache(getActivity());
                        mHandler.sendEmptyMessage(0);
                    }
                }).start();
                break;
            case R.id.home_my_tv_about:
                Intent intent = new Intent(_mActivity,AboutUsActivity.class);
                startActivity(intent);
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                Toast.makeText(_mActivity,"清理缓存成功",Toast.LENGTH_SHORT).show();
            }
        }
    };
}
