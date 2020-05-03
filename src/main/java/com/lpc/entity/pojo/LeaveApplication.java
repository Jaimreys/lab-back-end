package com.lpc.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class LeaveApplication {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Long proposerUsername;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reason;
    private String state;
    private String disapprovedReason;
    private Long checkerUsername;
    private LocalDateTime checkTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getProposerUsername() {
        return proposerUsername;
    }

    public void setProposerUsername(Long proposerUsername) {
        this.proposerUsername = proposerUsername;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getCheckerUsername() {
        return checkerUsername;
    }

    public void setCheckerUsername(Long checkerUsername) {
        this.checkerUsername = checkerUsername;
    }

    public LocalDateTime getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(LocalDateTime checkTime) {
        this.checkTime = checkTime;
    }

    public String getDisapprovedReason() {
        return disapprovedReason;
    }

    public void setDisapprovedReason(String disapprovedReason) {
        this.disapprovedReason = disapprovedReason;
    }
}
