package com.lpc.entity.enumeration;

public enum RoleEnum {
    // 以role_开头是SpringSecurity控制权所要求的
    // @Secured({"admin","teacher"})而数据库里存的角色就是role_admin，role_teacher
    ADMIN("role_admin"),
    TEACHER("role_teacher"),
    STUDENT("role_student");

    private String role;

    private RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
