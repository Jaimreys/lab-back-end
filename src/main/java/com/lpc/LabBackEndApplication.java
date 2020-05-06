package com.lpc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
// 开启mapper扫描
@MapperScan("com.lpc.dao")
// 开启事务
@EnableTransactionManagement
// Quartz开启任务调度
@EnableScheduling
// 开启SpirngSecurity
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class LabBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabBackEndApplication.class, args);
    }

}
