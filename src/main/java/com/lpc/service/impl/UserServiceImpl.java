package com.lpc.service.impl;

import com.lpc.dao.SystemUserMapper;
import com.lpc.entity.CustomizedException;
import com.lpc.entity.dto.SystemUserDTO;
import com.lpc.entity.pojo.SystemUser;
import com.lpc.util.SystemUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {
    private SystemUserMapper systemUserMapper;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(SystemUserMapper systemUserMapper,
                           @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.systemUserMapper = systemUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在数据库里面查询到这个用户
        SystemUser systemUser = systemUserMapper.selectSystemUserByUsername(username);
        //避免返回null
        return systemUser == null ? new SystemUser() : systemUser;
    }

    /**
     * 根据用户名获取系统用户信息
     */
    public SystemUser getSystemUserByUsername(String username) {
        return systemUserMapper.selectSystemUserByUsername(username);
    }

    /**
     * 更新用户密码
     */
    public void updatePassword(String oldPassword, String newPassword) {
        String username = SystemUserUtil.getUsername();
        SystemUser systemUser = this.getSystemUserByUsername(username);
        if (passwordEncoder.matches(oldPassword, systemUser.getPassword())) {
            //原密码正确，更新到新密码
            this.updatePasswordByUsername(username,
                    passwordEncoder.encode(newPassword));
        } else {
            throw new CustomizedException(4000, "原密码错误");
        }
    }

    /**
     * 根据用户id更新用户密码
     */
    private void updatePasswordByUsername(String username, String newPassword) {
        systemUserMapper.updatePasswordByUsername(username, newPassword);
    }

    /**
     * 初始化密码为123456
     */
    public void initializePassword(String username) {
        this.updatePasswordByUsername(username, passwordEncoder.encode("123456"));
    }

    /**
     * 获取用户信息
     */
    public List<SystemUserDTO> getSystemUsers(String realName) {
        return systemUserMapper.selectSystemUsers(realName);
    }

    /**
     * 获取用户的真实姓名
     */
    public String getRealName() {
        return systemUserMapper.selectUserRealNameByUsername(SystemUserUtil.getUsername());
    }

    /**
     * 删除用户
     */
    public void deleteSystemUser(String username) {
        systemUserMapper.deleteSystemUserByUsername(username);
    }

    /**
     * 更改用户角色
     */
    public void updateUserRole(SystemUser systemUser) {
        systemUserMapper.updateUserRole(systemUser);
    }

    /**
     * 添加用户
     */
    public void addSystemUser(SystemUser systemUser) {
        SystemUser oldUser = systemUserMapper.selectSystemUserByUsername(systemUser.getUsername());
        if (oldUser == null) {
            systemUser.setPassword(passwordEncoder.encode("123456"));
            systemUserMapper.insertSystemUser(systemUser);
        } else {
            throw new CustomizedException("添加的用户账号与已有账号重复");
        }
    }
}
