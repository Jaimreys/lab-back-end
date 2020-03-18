package com.lpc.service.impl;

import com.lpc.dao.SystemUserMapper;
import com.lpc.entity.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    private SystemUserMapper systemUserMapper;

    @Autowired
    public UserServiceImpl(SystemUserMapper systemUserMapper) {
        this.systemUserMapper = systemUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //在数据库里面查询到这个用户
        SystemUser systemUser = systemUserMapper.selectSystemUserByUsername(s);
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
     * 根据用户id更新用户密码
     */
    public void updatePasswordByUsername(String username, String password) {
        systemUserMapper.updatePasswordByUsername(username, password);
    }
}
