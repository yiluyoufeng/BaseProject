package com.pro.feng.hf.core.http.okhttp;


import com.pro.feng.hf.utils.AppUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;


public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {
        return new Cache(new File(AppUtils.getContext().getCacheDir().getAbsolutePath() + File
                .separator + "data/NetCache"),
                HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
