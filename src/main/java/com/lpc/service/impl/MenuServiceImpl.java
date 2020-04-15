package com.lpc.service.impl;

import com.lpc.dao.MenuMapper;
import com.lpc.dao.RoleMenuMapper;
import com.lpc.entity.pojo.Menu;
import com.lpc.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuMapper menuMapper;
    private final RoleMenuMapper roleMenuMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper,
                           RoleMenuMapper roleMenuMapper) {
        this.menuMapper = menuMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    /**
     * 获取所有菜单
     *
     * @param role 用户角色
     */
    @Override
    public List<Menu> getMenus(String role) {
        List<Integer> menuIds = roleMenuMapper.selectMenusByRole(role);
        return menuMapper.selectMenus(menuIds);
    }
}
