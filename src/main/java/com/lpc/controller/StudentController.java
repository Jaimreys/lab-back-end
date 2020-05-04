package com.lpc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lpc.entity.dto.StateStatisticsDTO;
import com.lpc.entity.pojo.SystemUser;
import com.lpc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public Page<SystemUser> getStudents(@RequestParam("pageNum") int pageNum,
                                        @RequestParam("pageSize") int pageSize,
                                        @RequestParam("realName") String realName) {
        return studentService.getStudents(pageNum,pageSize,realName);
    }

    // 两种统计，一种是统计每个人每天的状态，一种是统计每天每个人的状态
    @GetMapping("/student/month/states")
    public StateStatisticsDTO[] getStudentStateMonthly(@RequestParam("username") Long username,
                                                       @RequestParam("month") LocalDateTime startMonth) {
        return studentService.getStudentStateMonthly(username, startMonth);
    }
}
