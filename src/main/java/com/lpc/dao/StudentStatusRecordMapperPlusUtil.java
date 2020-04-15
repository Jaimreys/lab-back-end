package com.lpc.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lpc.entity.pojo.StudentStatusRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class StudentStatusRecordMapperPlusUtil {
    private StudentStatusRecordMapperPlus studentStatusRecordMapperPlus;

    @Autowired
    public StudentStatusRecordMapperPlusUtil(StudentStatusRecordMapperPlus studentStatusRecordMapperPlus) {
        this.studentStatusRecordMapperPlus = studentStatusRecordMapperPlus;
    }

    public List<StudentStatusRecord> selectStudentStatusMonthly(Long username,
                                                                LocalDate startTime,
                                                                LocalDate endTime) {
        QueryWrapper<StudentStatusRecord> queryWrapper
                = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StudentStatusRecord::getUsername, username)
                .between(StudentStatusRecord::getStatusStartDate, startTime, endTime);
        return studentStatusRecordMapperPlus.selectList(queryWrapper);
    }

}
