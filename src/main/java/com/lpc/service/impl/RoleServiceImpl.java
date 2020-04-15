package com.lpc.service.impl;

import com.lpc.entity.enumeration.RoleEnum;
import com.lpc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.swing.BakedArrayList;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    /**
     * 获取所有用户角色
     */
    @Override
    public List<String> getAllRoles() {
        RoleEnum[] roleEnums = RoleEnum.values();
        List<String> roles = new ArrayList<>();
        for (RoleEnum role : roleEnums) {
            roles.add(role.getRole());
        }
        return roles;
    }
}
