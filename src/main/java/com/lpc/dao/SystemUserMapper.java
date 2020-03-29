package com.lpc.dao;

import com.lpc.entity.dto.SystemUserDTO;
import com.lpc.entity.pojo.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemUserMapper {
    SystemUser selectSystemUserByUsername(String username);

    void updatePasswordByUsername(@Param("username") String username,
                                  @Param("password") String password);

    List<SystemUserDTO> selectSystemUsers(String realName);

    String selectUserRealNameByUsername(String username);

    void deleteSystemUserByUsername(String username);

    void updateUserRole(SystemUser systemUser);

    void insertSystemUser(SystemUser systemUser);
}