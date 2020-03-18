package com.lpc.controller;

import com.lpc.entity.Menu;
import com.lpc.service.MenuService;
import com.lpc.util.SystemUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {
    private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/menus")
    public List<Menu> getMenus() {
        int role = SystemUserUtil.getRole();
        return menuService.getMenus(role);
    }
}
