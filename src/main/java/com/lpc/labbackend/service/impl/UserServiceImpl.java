package com.lpc.labbackend.service.impl;

import com.lpc.labbackend.dao.SystemUserMapper;
import com.lpc.labbackend.entity.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    private SystemUserMapper systemUserMapper;

    //构造方法里的报错可以忽略
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
}
