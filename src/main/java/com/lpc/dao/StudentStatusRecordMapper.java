package com.lpc.dao;

import com.lpc.entity.pojo.StudentStatusRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StudentStatusRecordMapper {
    List<StudentStatusRecord> selectStudentStatusMonthly(@Param("username") Long username,
                                                         @Param("startTime") LocalDate startTime,
                                                         @Param("endTime") LocalDate endTime);
}