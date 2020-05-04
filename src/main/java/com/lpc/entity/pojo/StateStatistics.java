package com.lpc.entity.pojo;

/**
 * 状态统计信息
 */
public class StateStatistics {
    private String state;
    // 秒数
    private Integer duration;

    public StateStatistics() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
