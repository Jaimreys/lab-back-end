package com.lpc.entity.enumeration;

public enum LeaveApplicationStateEnum {
    AWAITING_CHECK("待审核"),
    APPROVED("同意"),
    DISAPPROVED("不同意")
    ;


    private String state;
    private LeaveApplicationStateEnum(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
