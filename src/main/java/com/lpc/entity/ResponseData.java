package com.lpc.entity;

import com.lpc.enumeration.HttpStatusEnum;

/**
 * 放到响应里的数据，由两部分构成
 */
public class ResponseData<T> {
    private int code;
    private String msg;
    private boolean isSuccessful;
    private T data;

    public ResponseData(T data, HttpStatusEnum httpStatusEnum, boolean isSuccessful) {
        this.code = httpStatusEnum.getCode();
        this.msg = httpStatusEnum.getMsg();
        this.isSuccessful = isSuccessful;
        this.data = data;
    }

    public ResponseData(int code, String msg, boolean isSuccessful, T data) {
        this.code = code;
        this.msg = msg;
        this.isSuccessful = isSuccessful;
        this.data = data;
    }

    public ResponseData() {
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStatus(HttpStatusEnum httpStatusEnum, boolean isSuccessful) {
        this.code = httpStatusEnum.getCode();
        this.msg = httpStatusEnum.getMsg();
        this.isSuccessful = isSuccessful;
        this.data = null;
    }
}
