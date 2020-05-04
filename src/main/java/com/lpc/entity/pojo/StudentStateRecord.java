package com.lpc.entity.pojo;

import java.time.LocalDate;
import java.time.LocalTime;

public class StudentStateRecord {
    private Integer id;

    private Long username;

    private String realName;

    private String state;

    private LocalDate stateStartDate;

    private LocalTime stateStartTime;

    private Integer stateDuration;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getStateStartDate() {
        return stateStartDate;
    }

    public void setStateStartDate(LocalDate stateStartDate) {
        this.stateStartDate = stateStartDate;
    }

    public LocalTime getStateStartTime() {
        return stateStartTime;
    }

    public void setStateStartTime(LocalTime stateStartTime) {
        this.stateStartTime = stateStartTime;
    }

    public Integer getStateDuration() {
        return stateDuration;
    }

    public void setStateDuration(Integer stateDuration) {
        this.stateDuration = stateDuration;
    }
}