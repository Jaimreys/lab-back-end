package com.lpc.service.impl;

import com.lpc.dao.LeaveApplicationMapperPlus;
import com.lpc.dao.LeaveApplicationMapperPlusUtil;
import com.lpc.entity.CustomizedException;
import com.lpc.entity.enumeration.LeaveApplicationStateEnum;
import com.lpc.entity.pojo.LeaveApplication;
import com.lpc.job.LeaveEndJob;
import com.lpc.job.LeaveStartJob;
import com.lpc.service.LeaveApplicationService;
import com.lpc.util.SystemUserUtil;
import org.apache.catalina.core.ApplicationContext;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {
    private Scheduler scheduler;
    private LeaveApplicationMapperPlus leaveApplicationMapperPlus;
    private LeaveApplicationMapperPlusUtil leaveApplicationMapperPlusUtil;

    @Autowired
    public LeaveApplicationServiceImpl(Scheduler scheduler,
                                       LeaveApplicationMapperPlus leaveApplicationMapperPlus,
                                       LeaveApplicationMapperPlusUtil leaveApplicationMapperPlusUtil) {
        this.scheduler = scheduler;
        this.leaveApplicationMapperPlus = leaveApplicationMapperPlus;
        this.leaveApplicationMapperPlusUtil = leaveApplicationMapperPlusUtil;
    }

    /**
     * 添加一个待审核的请假申请
     */
    @Override
    public void addLeaveApplication(LeaveApplication leaveApplication) {
        leaveApplication.setProposerUsername(SystemUserUtil.getUsername());
        // 状态设置为待审核
        leaveApplication.setState(LeaveApplicationStateEnum.AWAITING_CHECK.getState());
        leaveApplicationMapperPlus.insert(leaveApplication);
    }

    /**
     * 审核学生的请假申请
     */
    @Override
    public void checkLeaveApplication(LeaveApplication leaveApplication) {
        if (LeaveApplicationStateEnum.APPROVED.getState()
                .equals(leaveApplication.getState())) {
            // 如果请假申请被通过
            // 添加job和trigger
            addJobAndTrigger(leaveApplication);
        }
        // 更新到数据库中
        leaveApplication.setCheckerUsername(SystemUserUtil.getUsername());
        leaveApplication.setCheckTime(LocalDateTime.now());
        leaveApplicationMapperPlus.updateById(leaveApplication);
    }

    /**
     * 获取当前学生的所有请假申请
     */
    @Override
    public List<LeaveApplication> getAllLeaveApplications() {
        Long username = SystemUserUtil.getUsername();
        return leaveApplicationMapperPlusUtil.selectAllLeaveApplicationsByUsername(username);
    }

    /**
     * 获取所有符合状态的请假申请
     */
    @Override
    public List<LeaveApplication> getAllLeaveApplicationsByState(String state) {
        return leaveApplicationMapperPlusUtil.selectAllLeaveApplicationsByState(state);
    }

    /**
     * 添加job和trigger
     */
    private void addJobAndTrigger(LeaveApplication leaveApplication) {
        Long proposerUsername = leaveApplication.getProposerUsername();
        // 创建请假开始Job
        LocalDateTime startTime = leaveApplication.getStartTime();
        JobDetail startJobDetail = JobBuilder.newJob(LeaveStartJob.class)
                .withIdentity(leaveApplication.getStartTime().toString(),
                        proposerUsername + "_start")
                // 添加一些参数，执行的时候用
                .usingJobData("username", proposerUsername)
                .usingJobData("time", startTime.toString())
                .build();
        // 创建请假开始任务的触发器
        String startCron = String.format("%d %d %d %d %d ? %d",
                startTime.getSecond(),
                startTime.getMinute(),
                startTime.getHour(),
                startTime.getDayOfMonth(),
                startTime.getMonth().getValue(),
                startTime.getYear());
        CronTrigger startCronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(leaveApplication.getStartTime().toString(),
                        proposerUsername + "_start")
                .withSchedule(CronScheduleBuilder.cronSchedule(startCron))
                .build();

        // 创建请假结束Job
        LocalDateTime endTime = leaveApplication.getEndTime();
        JobDetail endJobDetail = JobBuilder.newJob(LeaveEndJob.class)
                .withIdentity(leaveApplication.getEndTime().toString(),
                        proposerUsername + "_end")
                // 添加一些参数，执行的时候用
                .usingJobData("username", proposerUsername)
                .usingJobData("time", endTime.toString())
                .build();
        // 创建请假结束任务的触发器
        String endCron = String.format("%d %d %d %d %d ? %d",
                endTime.getSecond(),
                endTime.getMinute(),
                endTime.getHour(),
                endTime.getDayOfMonth(),
                endTime.getMonth().getValue(),
                endTime.getYear());
        CronTrigger endCronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(leaveApplication.getStartTime().toString(),
                        proposerUsername + "_end")
                .withSchedule(CronScheduleBuilder.cronSchedule(endCron))
                .build();

        // 将job和trigger添加到scheduler里
        try {
            scheduler.scheduleJob(startJobDetail, startCronTrigger);
            scheduler.scheduleJob(endJobDetail, endCronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new CustomizedException("添加请假任务失败");
        }
    }
}
