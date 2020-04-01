package com.lpc.entity.pojo;

import java.sql.Time;
import java.util.Date;

public class StudentStatusRecord {
    private Integer id;

    private Long username;

    private String realName;

    private Integer statusId;

    private Date statusStartDate;

    private Date statusStartTime;

    private Date statusDuration;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUsername() {
        return username;
    }

    public void setUsername(Long username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Date getStatusStartDate() {
        return statusStartDate;
    }

    public void setStatusStartDate(Date statusStartDate) {
        this.statusStartDate = statusStartDate;
    }

    public Date getStatusStartTime() {
        return statusStartTime;
    }

    public void setStatusStartTime(Date statusStartTime) {
        this.statusStartTime = statusStartTime;
    }

    public Date getStatusDuration() {
        return statusDuration;
    }

    public void setStatusDuration(Date statusDuration) {
        this.statusDuration = statusDuration;
    }
}