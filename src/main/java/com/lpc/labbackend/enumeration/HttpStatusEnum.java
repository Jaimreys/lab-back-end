package com.lpc.labbackend.enumeration;

import org.springframework.http.HttpStatus;

public enum HttpStatusEnum {
    LOGIN_SUCCESSFUL("登录成功", 2000),

    LOGIN_UNSUCCESSFUL("登录失败，请检查账号密码", 4001),
    UN_LOGIN("尚未登录，请登录", 4002),
    TOKEN_EXPIRED("登录已过期，请重新登录", 4003);

    private String msg;
    private int code;

    HttpStatusEnum(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

}
