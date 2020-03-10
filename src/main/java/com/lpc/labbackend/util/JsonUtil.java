package com.lpc.labbackend.util;

import com.google.gson.Gson;
import com.lpc.labbackend.enumeration.HttpStatusEnum;

/**
 * json操作相关的工具类
 */
public class JsonUtil {
    public static String toJsonString(HttpStatusEnum httpStatusEnum) {
        Gson gson = new Gson();
        return gson.toJson(httpStatusEnum);
    }
}
