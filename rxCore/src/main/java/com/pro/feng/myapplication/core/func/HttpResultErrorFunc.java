package com.pro.feng.myapplication.core.func;


import com.pro.feng.myapplication.core.bean.ResultMode;
import com.pro.feng.myapplication.core.error.ApiException;
import com.pro.feng.myapplication.core.utils.LogUtils;

import rx.functions.Func1;

/**
 * Created by Feng
 */
public class HttpResultErrorFunc<T> implements Func1<ResultMode<T>, T> {
    @Override
    public T call(ResultMode<T> result) {
        if (result.getResult() != 1){
            LogUtils.e(result.getMsg());
            throw new ApiException(result.getResult(), result.getMsg());
        }
        return result.getData();
    }
}
