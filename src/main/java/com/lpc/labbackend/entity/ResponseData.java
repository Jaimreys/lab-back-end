package com.lpc.labbackend.entity;

import com.lpc.labbackend.enumeration.HttpStatusEnum;

/**
 * 放到响应里的数据，由两部分构成
 */
public class ResponseData<T> {
    private int code;
    private String msg;
    private T data;

    public ResponseData(T data, HttpStatusEnum httpStatusEnum) {
        this.code = httpStatusEnum.getCode();
        this.msg = httpStatusEnum.getMsg();
        this.data = data;
    }

    public ResponseData(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseData() {
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

    public void setStatus(HttpStatusEnum httpStatusEnum) {
        this.code = httpStatusEnum.getCode();
        this.msg = httpStatusEnum.getMsg();
    }
}
