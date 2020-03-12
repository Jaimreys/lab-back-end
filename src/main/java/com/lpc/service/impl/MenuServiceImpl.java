package com.lpc.service.impl;

import com.lpc.dao.MenuMapper;
import com.lpc.entity.Menu;
import com.lpc.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private MenuMapper menuMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper){
        this.menuMapper = menuMapper;
    }

    /**
     * 获取所有菜单
     */
    @Override
    public List<Menu> getMenus() {
        return menuMapper.selectMenus();
    }
}
