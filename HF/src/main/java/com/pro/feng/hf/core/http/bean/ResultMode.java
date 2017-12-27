package com.pro.feng.hf.core.http.bean;

/**
 * 接口返回结果基类
 *
 */
public class ResultMode<T> {
    /**
     * 结果 0表示失败，1表示成功
     */
    private int result;
    /**
     * 消息 失败时的消息提示
     */
    private String msg;

    private T data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultMode{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
