package com.lpc.entity;

import java.util.Date;

public class ExceptionRecord {
    private Integer id;
    private Integer code;
    private String msg;
    private String clazz;
    private String locationClass;
    private String locationMethod;
    private String locationRowNumber;
    private Date time;

    public ExceptionRecord() {
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

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getLocationClass() {
        return locationClass;
    }

    public void setLocationClass(String locationClass) {
        this.locationClass = locationClass;
    }

    public String getLocationMethod() {
        return locationMethod;
    }

    public void setLocationMethod(String locationMethod) {
        this.locationMethod = locationMethod;
    }

    public String getLocationRowNumber() {
        return locationRowNumber;
    }

    public void setLocationRowNumber(String locationRowNumber) {
        this.locationRowNumber = locationRowNumber;
    }
}
