package com.lpc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.lpc.component.LocalDateTimeDeserializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

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

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        JavaTimeModule module = new JavaTimeModule();
        // 序列化器
        module.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 反序列化器
        // 这里添加的是自定义的反序列化器
        module.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);

        // add converter at the very front
        // if there are same type mappers in converters, setting in first mapper is used.
        converters.add(0, new MappingJackson2HttpMessageConverter(mapper));
    }
}
