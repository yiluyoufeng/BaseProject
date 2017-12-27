package com.pro.feng.hf.core.http.func;


import com.pro.feng.hf.core.http.bean.ResultMode;
import com.pro.feng.hf.core.http.error.ApiException;
import com.pro.feng.hf.utils.LogUtils;

import io.reactivex.functions.Function;

/**
 * Created by Feng
 */
public class HttpResultErrorFunc<T> implements Function<ResultMode<T>, T> {

    @Override
    public T apply(ResultMode<T> result) throws Exception {
        if (result.getResult() != 1){
            LogUtils.e(result.getMsg());
            throw new ApiException(result.getResult(), result.getMsg());
        }
        return result.getData();
    }
}
