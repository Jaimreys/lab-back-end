package com.lpc.labbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置cors，解决前端访问后端的跨域资源问题
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //设置了前端的地址
                .allowedOrigins("http://localhost:10011")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS", "HEAD")
                .allowedHeaders("*")
                //将请求头里保存的token暴露出来给前端获取
                .exposedHeaders("Authorization");
    }
}
