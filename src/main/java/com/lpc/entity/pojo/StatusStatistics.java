package com.lpc.entity.pojo;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * 状态统计信息
 */
public class StatusStatistics {
    private Integer statusId;
    //2020/04/02 状态名称这个字段可能可以删除，如果删除，把StdentServiceImpl里的getStudentStatusMonthly()也改一下
    private String status;
    // todo 持续时间用什么类型表示，还有待商榷
    // 秒数
    private Integer duration;

    public StatusStatistics() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
