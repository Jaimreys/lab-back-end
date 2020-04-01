package com.lpc.service.impl;

import com.lpc.dao.EnumRoleMapper;
import com.lpc.entity.pojo.EnumRole;
import com.lpc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private EnumRoleMapper enumRoleMapper;

    @Autowired
    public RoleServiceImpl(EnumRoleMapper enumRoleMapper) {
        this.enumRoleMapper = enumRoleMapper;
    }

    /**
     * 获取所有用户角色
     */
    @Override
    public List<EnumRole> getAllRoles() {
        return enumRoleMapper.getAllRoles();
    }
}
