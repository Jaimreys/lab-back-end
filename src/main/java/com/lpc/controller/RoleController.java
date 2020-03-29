package com.lpc.controller;

import com.lpc.entity.pojo.EnumRole;
import com.lpc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {
    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public List<EnumRole> getAllRoles() {
        return roleService.getAllRoles();
    }
}
