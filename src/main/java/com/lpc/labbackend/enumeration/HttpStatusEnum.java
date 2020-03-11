package com.lpc.labbackend.enumeration;

import org.springframework.http.HttpStatus;

public enum HttpStatusEnum {
    SUCCESSFUL(2000, "成功"),

    LOGIN_UNSUCCESSFUL(4001, "登录失败，请检查账号密码"),
    UN_LOGIN(4002, "尚未登录，请登录"),
    TOKEN_EXPIRED(4003, "登录已过期，请重新登录"),

    INTERNAL_SERVER_ERROR(5000, "服务器内部错误")
    ;

    private String msg;
    private int code;

    HttpStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

}
