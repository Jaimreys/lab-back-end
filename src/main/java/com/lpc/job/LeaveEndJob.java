package com.lpc.job;

import com.lpc.dao.SystemUserMapperPlus;
import com.lpc.entity.pojo.SystemUser;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class LeaveEndJob extends QuartzJobBean {
    private Scheduler scheduler;
    private SystemUserMapperPlus systemUserMapperPlus;

    @Autowired
    public LeaveEndJob(Scheduler scheduler,
                       SystemUserMapperPlus systemUserMapperPlus) {
        this.scheduler = scheduler;
        this.systemUserMapperPlus = systemUserMapperPlus;
    }

    @Transactional
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
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
