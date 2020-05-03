package com.lpc.entity.pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StudentStatusRecord {
    private Integer id;

    private Long username;

    private String realName;

    private String status;

    private LocalDate statusStartDate;

    private LocalTime statusStartTime;

    private Integer statusDuration;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStatusStartDate() {
        return statusStartDate;
    }

    public void setStatusStartDate(LocalDate statusStartDate) {
        this.statusStartDate = statusStartDate;
    }

    public LocalTime getStatusStartTime() {
        return statusStartTime;
    }

    public void setStatusStartTime(LocalTime statusStartTime) {
        this.statusStartTime = statusStartTime;
    }

    public Integer getStatusDuration() {
        return statusDuration;
    }

    public void setStatusDuration(Integer statusDuration) {
        this.statusDuration = statusDuration;
    }
}