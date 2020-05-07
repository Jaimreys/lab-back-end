package com.lpc.controller;

import com.lpc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 获取所有用户角色
     *
     * @return 所有用户角色的List
     */
    @GetMapping("/roles")
    public List<String> getAllRoles() {
        return roleService.getAllRoles();
    }
}
