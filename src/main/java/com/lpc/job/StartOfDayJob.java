package com.lpc.job;

import com.lpc.entity.enumeration.StudentStateEnum;
import com.lpc.entity.pojo.SystemUser;
import com.lpc.service.StudentService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 每天早上将状态重置为休息
 * 打工的呢?
 */
@Component
public class StartOfDayJob extends QuartzJobBean {
    private StudentService studentService;

    @Autowired
    public StartOfDayJob(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 这里就有好多要考虑的了！
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        // 读取所有学生，然后将状态重置
        List<SystemUser> studentList = studentService.getAllStudents();
        for (SystemUser student : studentList) {
            if (student.getState() == null) {
                // 如果状态为空
                student.setState(StudentStateEnum.REST.getState());
            } else {

            }
        }
    }
}
