package com.lpc.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SystemUser implements UserDetails {
    @TableId(type = IdType.INPUT)
    private Long username;
    private String realName;
    private String password;
    private String role;
    private String state;

    public SystemUser() {
    }

    public void setUsername(Long username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //将数据库中的role字段保存在类的Authorities中
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(() -> {
            return this.role;
        });
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 这个方法是重写的，所以必须返回String
     */
    @Override
    public String getUsername() {
        return String.valueOf(username);
    }

    public Long getLongUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
