package com.lpc.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;

public class EnumStatus {
    @TableId
    private Integer id;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}