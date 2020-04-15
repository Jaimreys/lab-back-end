package com.lpc.entity.enumeration;

public enum RoleEnum {
    ADMIN("admin"),
    TEACHER("teacher"),
    STUDENT("student");

    private String role;

    private RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
