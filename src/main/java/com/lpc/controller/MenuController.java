package com.lpc.controller;

import com.lpc.entity.Menu;
import com.lpc.service.MenuService;
import com.lpc.util.JwtUtil;
import com.lpc.util.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
        int role = RoleUtil.getRole();
        return menuService.getMenus(role);
    }
}
