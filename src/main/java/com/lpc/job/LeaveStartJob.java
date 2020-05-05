package com.lpc.job;

import com.lpc.dao.SystemUserMapperPlus;
import com.lpc.entity.pojo.SystemUser;
import com.lpc.service.StudentService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 可以成功进行依赖注入
 */
@Component
public class LeaveStartJob extends QuartzJobBean {
    private Scheduler scheduler;
    private SystemUserMapperPlus systemUserMapperPlus;
    private StudentService studentService;

    @Autowired
    public LeaveStartJob(Scheduler scheduler,
                         SystemUserMapperPlus systemUserMapperPlus,
                         StudentService studentService) {
        this.scheduler = scheduler;
        this.systemUserMapperPlus = systemUserMapperPlus;
        this.studentService = studentService;
    }

    /**
     * 如果要添加事务，给service层方法添加，这里再调用service层方法
     */
    @Override
    public void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        Trigger trigger = jobExecutionContext.getTrigger();
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        // 将存进去的数据拿出来
        long username = jobDataMap.getLongValue("username");
        LocalDateTime time = LocalDateTime.parse(jobDataMap.getString("time"));

        // 编写要进行的逻辑
        SystemUser systemUser = systemUserMapperPlus.selectById(username);
        System.out.println(systemUser.getRealName());

        // 删除任务
        try {
            // 暂停触发器的计时
            scheduler.pauseTrigger(trigger.getKey());
            // 移除触发器中的任务
            scheduler.unscheduleJob(trigger.getKey());
            // 删除任务
            scheduler.deleteJob(jobDetail.getKey());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
