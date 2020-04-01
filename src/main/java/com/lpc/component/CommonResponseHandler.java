package com.lpc.component;

import com.lpc.entity.ResponseData;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 将返回的数据统一封装到自定义的ResponseData类中
 */
@RestControllerAdvice("com.lpc.controller")
public class CommonResponseHandler implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public ResponseData beforeBodyWrite(Object o,
                                        MethodParameter methodParameter,
                                        MediaType mediaType,
                                        Class aClass,
                                        ServerHttpRequest serverHttpRequest,
                                        ServerHttpResponse serverHttpResponse) {
        if (o instanceof ResponseData) {
            return (ResponseData) o;
        } else {
            return ResponseData.getInstance200(o);
        }
    }
}
