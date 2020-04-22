package com.lpc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({"dev"})
public class Swagger2Config {
    @Bean
    public Docket docket() {
        // http://localhost:10010/swagger-ui.html
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.groupName("fat-shallot")
                .apiInfo(getApiInfo());
        return docket;
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("前后端分离的实验室管理项目")
                .description("")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
