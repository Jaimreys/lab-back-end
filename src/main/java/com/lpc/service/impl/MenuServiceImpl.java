package com.lpc.service.impl;

import com.lpc.dao.MenuMapper;
import com.lpc.dao.RoleMenuMapper;
import com.lpc.entity.Menu;
import com.lpc.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private MenuMapper menuMapper;
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper,
                           RoleMenuMapper roleMenuMapper) {
        this.menuMapper = menuMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    /**
     * 获取所有菜单
     *
     * @param role 角色id
     */
    @Override
    public List<Menu> getMenus(int role) {
        List<Integer> menuIds = roleMenuMapper.selectMenuByRole(role);
        return menuMapper.selectMenus(menuIds);
    }
}
