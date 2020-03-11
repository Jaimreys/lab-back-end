package com.lpc.labbackend.entity;

import java.util.Date;

public class ExceptionRecord {
    private Integer id;
    private Integer code;
    private String msg;
    private Date time;

    public ExceptionRecord() {
    }

    public ExceptionRecord(Integer code, String msg, Date time) {
        this.code = code;
        this.msg = msg;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
