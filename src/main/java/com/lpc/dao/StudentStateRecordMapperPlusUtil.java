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

}
