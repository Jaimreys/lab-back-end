package com.lpc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lpc.entity.dto.StateStatisticsDTO;
import com.lpc.entity.pojo.SystemUser;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentService {
    List<SystemUser> getAllStudents();

    StateStatisticsDTO[] getStudentStateMonthly(Long username, LocalDateTime start);

    Page<SystemUser> getStudents(int pageNum, int pageSize, String realName);
}
