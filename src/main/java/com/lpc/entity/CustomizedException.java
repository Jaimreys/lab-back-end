package com.lpc.entity;

import com.lpc.entity.enumeration.HttpStatusEnum;
import org.springframework.http.HttpStatus;

/**
 * 自定义的全局异常
 */
public class CustomizedException extends RuntimeException {
    private int code;
    private String msg;

    public CustomizedException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CustomizedException(String msg) {
        //500
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.msg = msg;
    }

    public CustomizedException(HttpStatusEnum httpStatusEnum) {
        this.code = httpStatusEnum.getCode();
        this.msg = httpStatusEnum.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
