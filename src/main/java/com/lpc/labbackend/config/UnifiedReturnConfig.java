package com.lpc.labbackend.config;

import com.lpc.labbackend.entity.ResponseData;
import com.lpc.labbackend.enumeration.HttpStatusEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 将返回的数据统一封装到自定义的ResponseData类中
 */
@EnableWebMvc
@Configuration
public class UnifiedReturnConfig {

    @RestControllerAdvice("com.lpc.labbackend.controller")
    class CommonResponseAdvice implements ResponseBodyAdvice {

        @Override
        public boolean supports(MethodParameter methodParameter, Class aClass) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object o,
                                      MethodParameter methodParameter,
                                      MediaType mediaType,
                                      Class aClass,
                                      ServerHttpRequest serverHttpRequest,
                                      ServerHttpResponse serverHttpResponse) {
            if (o instanceof ResponseData) {
                return o;
            } else {
                return new ResponseData<Object>(o, HttpStatusEnum.SUCCESSFUL);
            }
        }
    }

}
