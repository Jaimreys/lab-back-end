package com.lpc.labbackend.controller;

import com.lpc.labbackend.dao.MenuMapper;
import com.lpc.labbackend.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {
    private MenuMapper menuMapper;

    @Autowired
    public MenuController(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @GetMapping("/menus")
    public List<Menu> getMenus() {
        return menuMapper.getMenus();
    }
}
