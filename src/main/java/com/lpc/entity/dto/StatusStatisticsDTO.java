package com.lpc.entity.dto;

public class StatusStatisticsDTO {
    //状态名，字段名name是为了前端图表使用
    private String name;
    //状态每天持续时间的秒数
    private Integer[] data;

    public StatusStatisticsDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer[] getData() {
        return data;
    }

    public void setData(Integer[] data) {
        this.data = data;
    }
}
