package com.lpc.entity;

import com.lpc.enumeration.HttpStatusEnum;

/**
 * 放到响应里的数据，由两部分构成
 */
public class ResponseData<T> {
    private int code;
    private String msg;
    private boolean successful;
    private T data;

    public ResponseData(HttpStatusEnum httpStatusEnum, boolean successful, T data) {
        this.code = httpStatusEnum.getCode();
        this.msg = httpStatusEnum.getMsg();
        this.successful = successful;
        this.data = data;
    }

    public ResponseData(int code, String msg, boolean successful, T data) {
        this.code = code;
        this.msg = msg;
        this.successful = successful;
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
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
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

    public void setStatus(HttpStatusEnum httpStatusEnum, boolean successful) {
        this.code = httpStatusEnum.getCode();
        this.msg = httpStatusEnum.getMsg();
        this.successful = successful;
        this.data = null;
    }

    /**
     * 响应成功时，将返回的数据包装成ResponseData对象
     */
    public static ResponseData<Object> getInstance200(Object o) {
        return new ResponseData<Object>(HttpStatusEnum.SUCCESSFUL, true, o);
    }
}
