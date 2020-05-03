package com.lpc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lpc.entity.enumeration.RoleEnum;
import com.lpc.entity.pojo.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentMapperPlusUtil {
    private SystemUserMapperPlus systemUserMapperPlus;

    @Autowired
    public StudentMapperPlusUtil(SystemUserMapperPlus systemUserMapperPlus) {
        this.systemUserMapperPlus = systemUserMapperPlus;
    }

    /**
     * 按条件获取学生
     */
    public Page<SystemUser> selectStudents(int pageNum, int pageSize, String realName) {
        Page<SystemUser> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        // 不查询密码
        queryWrapper.select(SystemUser.class, info -> !info.getColumn().equals("password"));
        LambdaQueryWrapper<SystemUser> lambdaQueryWrapper = queryWrapper.lambda();
        lambdaQueryWrapper.eq(SystemUser::getRole, RoleEnum.STUDENT.getRole());
        if(realName!=null&&!realName.isEmpty()){
            lambdaQueryWrapper.like(SystemUser::getRealName, realName);
        }
        systemUserMapperPlus.selectPage(page, queryWrapper);
        return page;
    }

    /**
     * 获取所有学生
     */
    public List<SystemUser> selectAllStudents() {
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        // 不查询密码
        queryWrapper.select(SystemUser.class, info -> !info.getColumn().equals("password"));
        queryWrapper.lambda()
                .eq(SystemUser::getRole, RoleEnum.STUDENT.getRole());
        return systemUserMapperPlus.selectList(queryWrapper);
    }
}
