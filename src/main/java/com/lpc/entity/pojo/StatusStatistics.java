package com.lpc.entity.pojo;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * 状态统计信息
 */
public class StatusStatistics {
    private String status;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
