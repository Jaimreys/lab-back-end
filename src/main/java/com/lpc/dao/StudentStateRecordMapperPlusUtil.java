package com.lpc.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lpc.entity.pojo.StudentStateRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class StudentStateRecordMapperPlusUtil {
    private StudentStateRecordMapperPlus studentStateRecordMapperPlus;

    @Autowired
    public StudentStateRecordMapperPlusUtil(StudentStateRecordMapperPlus studentStateRecordMapperPlus) {
        this.studentStateRecordMapperPlus = studentStateRecordMapperPlus;
    }

    /**
     * 获取某个月学生的状态
     */
    public List<StudentStateRecord> selectStudentStatusMonthly(Long username,
                                                               LocalDate startTime,
                                                               LocalDate endTime) {
        QueryWrapper<StudentStateRecord> queryWrapper
                = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StudentStateRecord::getUsername, username)
                .between(StudentStateRecord::getStateStartDate, startTime, endTime);
        return studentStateRecordMapperPlus.selectList(queryWrapper);
    }

    /**
     * 获取某一天学生的最后一个状态
     * 最后一个状态的state_duration字段肯定是null。因为要到第二天0点才会给这个字段赋值
     */
    public StudentStateRecord selectStudentHesternalLastState(Long username) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        QueryWrapper<StudentStateRecord> queryWrapper
                = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StudentStateRecord::getUsername, username)
                .eq(StudentStateRecord::getStateStartDate, yesterday)
                .isNull(StudentStateRecord::getStateDuration);
        return studentStateRecordMapperPlus.selectOne(queryWrapper);
    }

}
