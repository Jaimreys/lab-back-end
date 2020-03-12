package com.lpc.util;

import com.google.gson.Gson;
import com.lpc.entity.ResponseData;

/**
 * json操作相关的工具类
 */
public class JsonUtil {
    /**
     * 将分装好的响应对象转为json字符串
     */
    public static String responseData2JsonString(ResponseData data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }
}
