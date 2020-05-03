package com.lpc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.lpc.dao")
@EnableScheduling
public class LabBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabBackEndApplication.class, args);
    }

}
