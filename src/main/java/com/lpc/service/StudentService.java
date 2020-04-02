package com.lpc.service;

import com.lpc.entity.dto.StatusStatisticsDTO;
import com.lpc.entity.dto.StudentDTO;

import java.util.Calendar;
import java.util.List;

public interface StudentService {
    List<StudentDTO> getStudents(String realName);

    StatusStatisticsDTO[] getStudentStatusMonthly(Long username, Calendar start);
}
