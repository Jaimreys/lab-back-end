package com.lpc.dao;

import com.lpc.entity.SystemUser;

public interface SystemUserMapper {
    public SystemUser selectSystemUserByUsername(String username);
}