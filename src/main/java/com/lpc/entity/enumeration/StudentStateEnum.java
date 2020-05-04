package com.lpc.entity.enumeration;

public enum StudentStateEnum {
    STUDY(1,"学习"),
    REST(2,"休息"),
    LEAVE(3,"放假"),
    CLASS(4,"上课"),
    WORK(5,"打工")
    ;

    private Integer id;
    private String state;

    private StudentStateEnum(Integer id, String state) {
        this.id = id;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public String getState() {
        return state;
    }
}
