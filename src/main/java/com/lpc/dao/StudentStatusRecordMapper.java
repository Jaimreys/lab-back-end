package com.lpc.dao;

import com.lpc.entity.pojo.StudentStatusRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StudentStatusRecordMapper {
    List<StudentStatusRecord> selectStudentStatusMonthly(@Param("username") Long username,
                                                         @Param("startTime") Date startTime,
                                                         @Param("endTime") Date endTime);
}