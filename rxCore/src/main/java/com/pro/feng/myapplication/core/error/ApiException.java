package com.pro.feng.myapplication.core.error;

/**
 * Created by Feng on 2017/12/13.
 */

public class ApiException extends RuntimeException{

    public static final int NO_FUN_RESULT = -2;//无权限

    private int result;
    /**
     * 消息 失败时的消息提示
     */
    private String msg;
    public ApiException(int result, String msg){
        this.result = result;
        this.msg = msg;
    }

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

    @Override
    public String toString() {
        return "ApiException{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                '}';
    }
}
