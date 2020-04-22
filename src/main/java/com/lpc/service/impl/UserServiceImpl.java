package com.lpc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lpc.dao.SystemUserMapperPlus;
import com.lpc.entity.CustomizedException;
import com.lpc.entity.pojo.SystemUser;
import com.lpc.util.SystemUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final SystemUserMapperPlus systemUserMapperPlus;

    @Autowired
    public UserServiceImpl(@Lazy BCryptPasswordEncoder passwordEncoder,
                           SystemUserMapperPlus systemUserMapperPlus) {
        this.passwordEncoder = passwordEncoder;
        this.systemUserMapperPlus = systemUserMapperPlus;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在数据库里面查询到这个用户
        SystemUser systemUser = systemUserMapperPlus.selectById(username);

        //避免返回null
        return systemUser == null ? new SystemUser() : systemUser;
    }

    /**
     * 根据用户名获取系统用户信息
     */
    public SystemUser getSystemUserByUsername(Long username) {
        return systemUserMapperPlus.selectById(username);
    }

    /**
     * 用户自己更新自己的密码
     */
    public void updatePassword(String oldPassword, String newPassword) {
        Long username = SystemUserUtil.getUsername();
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
    private void updatePasswordByUsername(Long username, String newPassword) {
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(username);
        systemUser.setPassword(newPassword);
        systemUserMapperPlus.updateById(systemUser);
    }

    /**
     * 初始化密码为123456
     */
    public void initializePassword(Long username) {
        this.updatePasswordByUsername(username, passwordEncoder.encode("123456"));
    }

    /**
     * 获取用户信息。需要分页和模糊查询，分页已经在控制层做好了
     */
    public Page<SystemUser> getSystemUsers(int pageNum, int pageSize, String realName) {
        Page<SystemUser> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        // 不将密码传送到前台
        queryWrapper.select(SystemUser.class, info -> !info.getColumn().equals("password"));
        queryWrapper.lambda()
                .like(SystemUser::getRealName, realName);
        systemUserMapperPlus.selectPage(page, queryWrapper);
        return page;
    }

    /**
     * 获取用户的真实姓名
     */
    public String getRealName() {
        SystemUser systemUser = systemUserMapperPlus.selectById(SystemUserUtil.getUsername());
        return systemUser.getRealName();
    }

    /**
     * 删除用户
     */
    public void deleteSystemUser(Long username) {
        systemUserMapperPlus.deleteById(username);
    }

    /**
     * 更改用户角色
     */
    public void updateUserRole(SystemUser systemUser) {
        systemUserMapperPlus.updateById(systemUser);
    }

    /**
     * 添加用户
     */
    public void addSystemUser(SystemUser systemUser) {
        SystemUser oldUser = systemUserMapperPlus.selectById(systemUser.getLongUsername());
        if (oldUser == null) {
            systemUser.setPassword(passwordEncoder.encode("123456"));
            systemUserMapperPlus.insert(systemUser);
        } else {
            throw new CustomizedException("添加的用户账号与已有账号重复");
        }
    }
}
