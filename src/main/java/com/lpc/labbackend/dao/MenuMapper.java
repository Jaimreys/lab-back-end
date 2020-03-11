package com.lpc.labbackend.dao;

import com.lpc.labbackend.entity.Menu;

import java.util.List;

public interface MenuMapper {
    public List<Menu> selectMenus();
}