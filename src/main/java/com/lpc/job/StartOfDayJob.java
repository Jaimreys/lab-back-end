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
     * 在0点重置学生的状态，并插入新状态数据
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        studentService.initStudentsState0oclockEveryday();
    }
}
