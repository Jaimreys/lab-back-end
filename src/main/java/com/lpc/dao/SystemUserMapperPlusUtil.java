package com.lpc.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lpc.entity.pojo.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemUserMapperPlusUtil {
    public SystemUserMapperPlus systemUserMapperPlus;

    @Autowired
    public SystemUserMapperPlusUtil(SystemUserMapperPlus systemUserMapperPlus) {
        this.systemUserMapperPlus = systemUserMapperPlus;
    }

    /**
     * 获取系统用户并分页
     *
     * @param pageNum  页数
     * @param pageSize 每页数据数
     * @param realName 查询条件：系统用户真实姓名，或其一部分
     */
    public Page<SystemUser> selectSystemUsersPaging(int pageNum, int pageSize, String realName) {
        Page<SystemUser> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        // 不查询密码
        queryWrapper.select(SystemUser.class, info -> !info.getColumn().equals("password"));
        if (realName != null && !realName.isEmpty()) {
            queryWrapper.lambda()
                    .like(SystemUser::getRealName, realName);
        }
        systemUserMapperPlus.selectPage(page, queryWrapper);
        return page;
    }
}
