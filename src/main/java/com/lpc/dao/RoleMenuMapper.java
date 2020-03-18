package com.lpc.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuMapper {
    List<Integer> selectMenuByRole(int role);
}
