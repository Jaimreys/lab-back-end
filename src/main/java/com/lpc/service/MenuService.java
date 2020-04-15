package com.lpc.service;

import com.lpc.entity.pojo.Menu;

import java.util.List;

public interface MenuService {
    public List<Menu> getMenus(String role);
}
