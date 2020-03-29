package com.lpc.dao;

import com.lpc.entity.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper {
    List<Menu> selectMenus(List<Integer> menuIds);
}