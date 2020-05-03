package com.lpc.entity.enumeration;

public enum  StudentStatusEnum {
    STUDY(1,"学习"),
    REST(2,"休息"),
    LEAVE(3,"放假"),
    CLASS(4,"上课"),
    WORK(5,"打工")
    ;

    private Integer id;
    private String status;

    private StudentStatusEnum(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
