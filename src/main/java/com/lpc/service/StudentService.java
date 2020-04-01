package com.lpc.service;

import com.lpc.entity.dto.StudentDTO;
import com.lpc.entity.pojo.StatusStatistics;

import java.util.Calendar;
import java.util.List;

public interface StudentService {
    List<StudentDTO> getStudents(String realName);

    List<StatusStatistics>[] getStudentStatusMonthly(Long username, Calendar start);
}
