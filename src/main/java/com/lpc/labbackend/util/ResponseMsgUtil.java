package com.lpc.labbackend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpc.labbackend.enumeration.HttpStatusEnum;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ResponseMsgUtil {
    public static void setResponseMsg(HttpServletResponse response,
                                      HttpStatusEnum httpStatusEnum) {
        try {
            response.setContentType("application/json;charset=utf-8");
            //状态码如果不是200，Vue代码直接就异常终止了，我佛了
            response.setStatus(HttpStatus.OK.value());
            PrintWriter writer = response.getWriter();
            Map responseMap = new HashMap(2);
            responseMap.put("code", httpStatusEnum.getCode());
            responseMap.put("msg", httpStatusEnum.getMsg());
            writer.write(new ObjectMapper().writeValueAsString(responseMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
