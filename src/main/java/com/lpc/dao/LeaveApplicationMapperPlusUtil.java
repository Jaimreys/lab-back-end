package com.lpc.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lpc.entity.pojo.LeaveApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeaveApplicationMapperPlusUtil {
    private LeaveApplicationMapperPlus leaveApplicationMapperPlus;

    @Autowired
    public LeaveApplicationMapperPlusUtil(LeaveApplicationMapperPlus leaveApplicationMapperPlus) {
        this.leaveApplicationMapperPlus = leaveApplicationMapperPlus;
    }

    /**
     * 获取当前学生的所有请假申请
     */
    public List<LeaveApplication> selectAllLeaveApplicationsByUsername(Long username) {
        QueryWrapper<LeaveApplication> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(LeaveApplication::getProposerUsername, username);
        return leaveApplicationMapperPlus.selectList(queryWrapper);
    }

    /**
     * 获取所有符合状态的请假申请
     */
    public List<LeaveApplication> selectAllLeaveApplicationsByState(String state) {
        QueryWrapper<LeaveApplication> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(LeaveApplication::getState, state);
        return leaveApplicationMapperPlus.selectList(queryWrapper);
    }
}
