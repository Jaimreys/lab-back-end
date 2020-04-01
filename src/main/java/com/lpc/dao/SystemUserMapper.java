package com.lpc.dao;

import com.lpc.entity.dto.StudentDTO;
import com.lpc.entity.dto.SystemUserRoleDTO;
import com.lpc.entity.pojo.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemUserMapper {
    SystemUser selectSystemUserByUsername(Long username);

    void updatePasswordByUsername(@Param("username") Long username,
                                  @Param("password") String password);

    List<SystemUserRoleDTO> selectSystemUsers(String realName);

    String selectUserRealNameByUsername(Long username);

    void deleteSystemUserByUsername(Long username);

    void updateUserRole(SystemUser systemUser);

    void insertSystemUser(SystemUser systemUser);

    List<StudentDTO> selectStudents(String realName);
}