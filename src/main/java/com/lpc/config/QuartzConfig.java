package com.lpc.config;

import com.lpc.job.StartOfDayJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz的相关配置，注册JobDetail和Trigger
 * <p>
 * 设置执行间隔方式1：
 * .withSchedule(SimpleScheduleBuilder
 * .simpleSchedule()
 * .withIntervalInMinutes(1)
 * .repeatForever())
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        JobDetail jobDetail = JobBuilder.newJob(StartOfDayJob.class)
                .withIdentity("start_of_day", "start_of_day")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger trigger() {
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("start_of_day", "start_of_day")
                .startNow()
                // 每天0点执行
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
                .build();
        return trigger;
    }
}
