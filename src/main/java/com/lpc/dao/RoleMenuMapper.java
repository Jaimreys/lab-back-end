package com.lpc.dao;

import java.util.List;

public interface RoleMenuMapper {
    public List<Integer> selectMenuByRole(int role);
}
