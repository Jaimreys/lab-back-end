package com.lpc.labbackend.service.impl;

import com.lpc.labbackend.dao.MenuMapper;
import com.lpc.labbackend.entity.Menu;
import com.lpc.labbackend.service.MenuService;
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

    @Override
    public List<Menu> getMenus() {
        return menuMapper.selectMenus();
    }
}
