package com.lpc.controller;

import com.lpc.entity.pojo.Menu;
import com.lpc.service.MenuService;
import com.lpc.util.SystemUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 获取当前用户拥有权限的菜单
     *
     * @return 当前用户拥有权限的按钮List
     */
    @GetMapping("/menus")
    public List<Menu> getMenus() {
        String role = SystemUserUtil.getRole();
        return menuService.getMenus(role);
    }
}
