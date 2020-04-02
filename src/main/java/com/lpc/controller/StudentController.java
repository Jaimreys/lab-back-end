package com.lpc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lpc.entity.dto.StatusStatisticsDTO;
import com.lpc.entity.dto.StudentDTO;
import com.lpc.entity.pojo.StatusStatistics;
import com.lpc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public PageInfo<StudentDTO> getStudents(@RequestParam("pageNum") int pageNum,
                                            @RequestParam("pageSize") int pageSize,
                                            @RequestParam("realName") String realName) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentDTO> students = studentService.getStudents(realName);
        return new PageInfo<>(students);
    }

    // 两种统计，一种是统计每个人每天的状态，一种是统计每天每个人的状态
    @GetMapping("/student/month/status")
    public StatusStatisticsDTO[] getStudentStatusMonthly(@RequestParam("username") Long username,
                                                         @RequestParam("month") LocalDateTime startMonth) {
        return studentService.getStudentStatusMonthly(username, startMonth);
    }
}
