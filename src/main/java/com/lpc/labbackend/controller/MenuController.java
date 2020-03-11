package com.lpc.labbackend.controller;

import com.lpc.labbackend.dao.MenuMapper;
import com.lpc.labbackend.entity.CustomizedException;
import com.lpc.labbackend.entity.Menu;
import com.lpc.labbackend.service.MenuService;
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
        throw new CustomizedException(5000, "异常测试");
//        return menuService.selectMenus();
    }
}
