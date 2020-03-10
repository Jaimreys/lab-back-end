package com.lpc.labbackend.dao;

import com.lpc.labbackend.entity.SystemUser;

public interface SystemUserMapper {
    public SystemUser getSystemUserByUsername(String username);
}