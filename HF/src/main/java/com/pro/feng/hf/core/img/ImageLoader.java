package com.pro.feng.hf.core.img;

import android.content.Context;

import com.bumptech.glide.Glide;

/**
 * Created by Feng on 2017/12/23.
 */

public class ImageLoader {
    //清理磁盘缓存,需要在子线程中执行
    public static void clearImageDiskCache(Context mContext) {
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存,可以在UI主线程中进行
    public static void clearImageMemory(Context mContext) {
        Glide.get(mContext).clearMemory();
    }
}
