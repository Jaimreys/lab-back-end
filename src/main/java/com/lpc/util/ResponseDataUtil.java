package com.lpc.util;

import com.lpc.entity.ResponseData;
import com.lpc.enumeration.HttpStatusEnum;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登陆以及token校验时将数据封装到响应中
 * 查询数据时，将数据封装成统一的类
 */
public class ResponseDataUtil {

    /**
     * 将获取的数据和相应的相应状态写到响应里
     * 登录和token校验的时候使用
     * 给响应状态赋值200
     */
    private static <T> void setDataInResponse(HttpServletResponse response,
                                              T data,
                                              HttpStatusEnum status,
                                              HttpStatus httpStatus,
                                              boolean isSuccessful)
            throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(httpStatus.value());
        PrintWriter writer = response.getWriter();
        writer.write(JsonUtil.responseData2JsonString(new ResponseData<T>(status, isSuccessful, data)));
        writer.flush();
        writer.close();
    }

    /**
     * 将获取的数据和相应的相应状态写到响应里
     * 登录和token校验的时候使用
     * <p>
     * 如果不写下面两行，前端会报404.如果写这两行，后端会报错说响应的sendError()方法已提交，然后报错
     * writer.flush();
     * writer.close();
     */
    public static <T> void setDataInResponse(HttpServletResponse response,
                                             T data,
                                             HttpStatusEnum status,
                                             boolean isSuccessful) {
        try {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JsonUtil.responseData2JsonString(new ResponseData<T>(status, isSuccessful, data)));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将获取的数据和相应的相应状态写到响应里
     * 登录和token校验的时候使用
     * 给响应状态赋值200
     */
    public static <T> void setDataInResponse200(HttpServletResponse response,
                                                T data,
                                                HttpStatusEnum statusEnum) {
        try {
            setDataInResponse(response, data, statusEnum, HttpStatus.OK, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将获取的数据和相应的相应状态写到响应里
     * 登录和token校验的时候使用
     * 给响应状态赋值400
     */
    public static <T> void setDataInResponse400(HttpServletResponse response,
                                                T data,
                                                HttpStatusEnum statusEnum) {
        try {
            setDataInResponse(response, data, statusEnum, HttpStatus.BAD_REQUEST, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将获取的数据和相应的相应状态写到响应里
     * 登录和token校验的时候使用
     * 给响应状态赋值500
     */
    public static <T> void setDataInResponse500(HttpServletResponse response,
                                                T data,
                                                HttpStatusEnum statusEnum) {
        try {
            setDataInResponse(response, data, statusEnum, HttpStatus.INTERNAL_SERVER_ERROR, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接返回成功消息以及数据
     */
    public static <T> ResponseData<T> success(T data) {
        ResponseData<T> responseData = new ResponseData<T>(HttpStatusEnum.SUCCESSFUL, true, data);
        return responseData;
    }
}
