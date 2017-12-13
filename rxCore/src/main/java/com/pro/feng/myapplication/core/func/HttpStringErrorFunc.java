package com.pro.feng.myapplication.core.func;

import com.pro.feng.myapplication.core.error.ApiException;
import com.pro.feng.myapplication.core.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import rx.functions.Func1;

/**
 * Created by Feng on 2017/3/7.
 */

public class HttpStringErrorFunc implements Func1<String, String> {
    @Override
    public String call(String netStr) {
        try {

            JSONObject jsonObject = new JSONObject(netStr);
            int result = jsonObject.optInt("result");
            String msg = jsonObject.optString("msg");
            if (result != 1) {
                LogUtils.e(msg);
                throw new ApiException(result, msg);
            }
            return jsonObject.optString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
