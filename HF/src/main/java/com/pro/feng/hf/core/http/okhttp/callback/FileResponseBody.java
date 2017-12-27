package com.pro.feng.hf.core.http.okhttp.callback;



import com.pro.feng.hf.core.http.rx.RxSchedulers;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Feng
 */

public class FileResponseBody<T> extends ResponseBody {
    /**
     * 实际请求体
     */
    private ResponseBody mResponseBody;
    /**
     * 下载回调接口
     */
    private RetrofitCallBack<T> mCallback;
    /**
     * BufferedSource
     */
    private BufferedSource mBufferedSource;
    public FileResponseBody(ResponseBody responseBody, RetrofitCallBack<T> callback) {
        super();
        this.mResponseBody = responseBody;
        this.mCallback = callback;
    }
    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }
    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }
    /**
     * 回调进度接口
     * @param source
     * @return Source
     */
    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            int currentProgress;
            long contentLength = 0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //回调
                Observable.just(null).compose(RxSchedulers.io_main()).filter(o -> {
                    int progress = (int)(totalBytesRead * 100.0 / contentLength);
                    if (progress > currentProgress){
                        currentProgress = progress;
                        return true;
                    }
                    return false;
                }).subscribe(o ->
                        mCallback.onLoading(currentProgress)
                );
                return bytesRead;
            }
        };
    }
}