package com.lpc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lpc.entity.dto.StateStatisticsDTO;
import com.lpc.entity.pojo.SystemUser;
import com.lpc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;

@RestController
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 分页获取所有符合条件的学生
     *
     * @param pageNum  当前页码
     * @param pageSize 每页数据条数
     * @param realName 学生实姓名，支持模糊查询
     * @return 用Page包装的学生List，含有数据总条数等信息
     */
    @Secured({"admin", "teacher"})
    @GetMapping("/students")
    public Page<SystemUser> getStudents(@RequestParam("pageNum") int pageNum,
                                        @RequestParam("pageSize") int pageSize,
                                        @RequestParam("realName") String realName) {
        return studentService.getStudents(pageNum, pageSize, realName);
    }

    /**
     * 按月统计学生的每种状态
     *
     * @param username   学生的用户名
     * @param startMonth 统计状态的月份
     * @return 经过处理的数据格式，包含一个月的每一天的每种状态的时间
     */
    @GetMapping("/student/month/states")
    public StateStatisticsDTO[] getStudentStateMonthly(@RequestParam("username") Long username,
                                                       @RequestParam("month") LocalDateTime startMonth) {
        return studentService.getStudentStateMonthly(username, startMonth);
    }
}
