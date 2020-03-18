package com.lpc.dao;

import com.lpc.entity.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserMapper {
    SystemUser selectSystemUserByUsername(String username);

    void updatePasswordByUsername(@Param("username") String username,
                                  @Param("password") String password);
}