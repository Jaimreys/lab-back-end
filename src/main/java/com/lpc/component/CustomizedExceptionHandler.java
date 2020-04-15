package com.lpc.component;

import com.lpc.dao.ExceptionRecordMapperPlus;
import com.lpc.entity.CustomizedException;
import com.lpc.entity.pojo.ExceptionRecord;
import com.lpc.entity.ResponseData;
import com.lpc.entity.enumeration.HttpStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 全局的异常处理器
 * Throwable类的getStackTrace()获取所有异常信息数组，其中第一个就是报异常的位置
 * Throwable类的toString()返回类名
 */
@RestControllerAdvice
public class CustomizedExceptionHandler {
    @Autowired
    private ExceptionRecordMapperPlus exceptionRecordMapperPlus;

    private static final Logger logger
            = LoggerFactory.getLogger(CustomizedExceptionHandler.class);

    /**
     * 捕获自定义的异常
     */
    @ExceptionHandler(value = CustomizedException.class)
    public ResponseData customizedExceptionHandle(HttpServletResponse response, CustomizedException ex) {
        String location = ex.getStackTrace()[0].toString();
        String locationRowNumber = location.substring(location.lastIndexOf(":") + 1, location.length() - 1);
        //获取倒数第二个点号的位置。搜索的位置从倒数第一个点号的前一位往前找
        int penultimateIndex = location.lastIndexOf(".", location.lastIndexOf(".") - 1);
        String locationMethod = location.substring(penultimateIndex + 1, location.lastIndexOf("(")) + "()";
        String locationClass = location.substring(0, penultimateIndex);

        //打印错误位置
        logger.error("错误位置：" + location);
        //打印错误信息
        //这个重载可以将Trowable接口的子对象传入，然后用日志打印出错误栈
        logger.error("错误信息", ex);

        //把异常存入数据库方便查看
        ExceptionRecord exceptionRecord = new ExceptionRecord();
        exceptionRecord.setCode(ex.getCode());
        exceptionRecord.setMsg(ex.getMsg());
        exceptionRecord.setTime(new Date(System.currentTimeMillis()));
        exceptionRecord.setClazz(ex.toString());
        exceptionRecord.setLocationClass(locationClass);
        exceptionRecord.setLocationMethod(locationMethod);
        exceptionRecord.setLocationRowNumber(locationRowNumber);

        //插入到数据库
        exceptionRecordMapperPlus.insert(exceptionRecord);

        //给响应设置状态码
        if (ex.getCode() >= 4000 && ex.getCode() < 5000) {
            //400
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else {
            //500
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return new ResponseData<>(ex.getCode(),
                ex.getMsg(),
                false,
                null);
    }

    /**
     * 捕获Java的异常类
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseData customizedExceptionHandle(HttpServletResponse response, Exception ex) {
        String location = ex.getStackTrace()[0].toString();
        String msg = ex.getMessage() == null ? ex.toString() : ex.getMessage();
        String exStr = ex.toString();
        String clazz = exStr.contains(":") ? exStr.substring(0, exStr.indexOf(":")) : exStr;
        String locationRowNumber = location.substring(location.lastIndexOf(":") + 1, location.length() - 1);
        //获取倒数第二个点号的位置。搜索的位置从倒数第一个点号的前一位往前找
        int penultimateIndex = location.lastIndexOf(".", location.lastIndexOf(".") - 1);
        String locationMethod = location.substring(penultimateIndex + 1, location.lastIndexOf("(")) + "()";
        String locationClass = location.substring(0, penultimateIndex);

        //打印错误位置
        logger.error("错误位置：" + location);
        //打印错误信息
        //这个重载可以将Trowable接口的子对象传入，然后用日志打印出错误栈
        logger.error("错误信息", ex);

        //把异常存入数据库方便查看
        ExceptionRecord exceptionRecord = new ExceptionRecord();
        exceptionRecord.setCode(HttpStatusEnum.INTERNAL_SERVER_ERROR.getCode());
        exceptionRecord.setMsg(msg);
        exceptionRecord.setTime(new Date(System.currentTimeMillis()));
        exceptionRecord.setClazz(clazz);
        exceptionRecord.setLocationClass(locationClass);
        exceptionRecord.setLocationMethod(locationMethod);
        exceptionRecord.setLocationRowNumber(locationRowNumber);

        //插入到数据库
        exceptionRecordMapperPlus.insert(exceptionRecord);

        //给响应设置状态码
        //500
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseData<>(HttpStatusEnum.INTERNAL_SERVER_ERROR,
                false,
                null);
    }
}

