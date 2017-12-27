package com.pro.feng.hf.core.http.func;


import com.pro.feng.hf.core.http.error.ApiException;
import com.pro.feng.hf.utils.LogUtils;

import org.json.JSONObject;

import io.reactivex.functions.Function;

/**
 * Created by Feng on 2017/3/7.
 */

public class HttpStringErrorFunc implements Function<String, String> {

    @Override
    public String apply(String netStr) throws Exception {
        JSONObject jsonObject = new JSONObject(netStr);
        int result = jsonObject.optInt("result");
        String msg = jsonObject.optString("msg");
        if (result != 1) {
            LogUtils.e(msg);
            throw new ApiException(result, msg);
        }
        return jsonObject.optString("data");

    }
}
