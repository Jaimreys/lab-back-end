package com.lpc.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Iterator;

public class SystemUserUtil {
    /**
     * 获取登录后保存下来的的用户角色
     */
    public static int getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        String role = "0";
        if (iterator.hasNext()) {
            role = iterator.next().getAuthority();
        }
        return Integer.valueOf(role);
    }

    /**
     * 获取登录后保存下来的用户名
     */
    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}