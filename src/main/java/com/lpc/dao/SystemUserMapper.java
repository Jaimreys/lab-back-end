package com.lpc.dao;

import com.lpc.entity.SystemUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserMapper {
    public SystemUser selectSystemUserByUsername(String username);
}