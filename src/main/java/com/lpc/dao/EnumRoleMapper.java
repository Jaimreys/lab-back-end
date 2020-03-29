package com.lpc.dao;

import com.lpc.entity.pojo.EnumRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnumRoleMapper {
    List<EnumRole> getAllRoles();
}