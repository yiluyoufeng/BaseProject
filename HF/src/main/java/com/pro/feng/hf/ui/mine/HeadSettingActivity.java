package com.pro.feng.hf.ui.mine;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pro.feng.hf.R;
import com.pro.feng.hf.base.BaseActivity;
import com.pro.feng.hf.bean.RxEventHeadBean;
import com.pro.feng.hf.contact.interfaces.mine.HeadSettingContact;
import com.pro.feng.hf.contact.presenter.mine.HeadSettingPresenter;
import com.pro.feng.hf.core.http.rx.RxSchedulers;
import com.pro.feng.hf.core.mvpbase.factory.CreatePresenter;
import com.pro.feng.hf.rxbus.RxBus;
import com.pro.feng.hf.utils.FinalDataUtil;
import com.pro.feng.hf.widget.group.headclip.ClipViewLayout;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

import static com.pro.feng.hf.contact.presenter.mine.MinePresenter.HEAD_IMAGE_NAME;

/**
 * Created by Feng on 2017/12/22.
 */
@CreatePresenter(HeadSettingPresenter.class)
public class HeadSettingActivity extends BaseActivity<HeadSettingContact,HeadSettingPresenter> implements HeadSettingContact, View.OnClickListener {
    ClipViewLayout cvlRect;
    TextView tvCancel,tvOk;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_head_setting);
        cvlRect = findViewById(R.id.cvl_rect);
        tvCancel = findViewById(R.id.tv_cancel);
        tvOk = findViewById(R.id.tv_ok);
        tvCancel.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        initToolBar("头像截取");
        cvlRect.setImageSrc(getIntent().getData());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    /**
     * 生成Uri
     */
    private Uri generateUri() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        zoomedCropBitmap = cvlRect.clip();
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), HEAD_IMAGE_NAME + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mSaveUri;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                onBackPressedSupport();
                break;
            case R.id.tv_ok:
                Observable.create(new ObservableOnSubscribe<Uri>() {
                    @Override
                    public void subscribe(ObservableEmitter<Uri> e) throws
                            Exception {
                        e.onNext(generateUri());
                        e.onComplete();
                    }
                }).compose(RxSchedulers.<Uri>io_main())
                        .subscribe(new Consumer<Uri>() {
                            @Override
                            public void accept(Uri uri) throws Exception {
                                RxEventHeadBean rxEventHeadBean = new RxEventHeadBean(uri);
                                RxBus.get().send(FinalDataUtil.RX_BUS_CODE_HEAD_IMAGE_URI, rxEventHeadBean);
                                onBackPressedSupport();
                            }
                        });
                break;
        }
    }
}
