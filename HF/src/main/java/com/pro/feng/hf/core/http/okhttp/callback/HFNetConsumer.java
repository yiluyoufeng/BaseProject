package com.pro.feng.hf.core.http.okhttp.callback;

import io.reactivex.functions.Consumer;

/**
 * Created by Feng on 2017/12/21.
 */

public interface HFNetConsumer<T> extends Consumer<T>{

     void error(Throwable throwable);
}
