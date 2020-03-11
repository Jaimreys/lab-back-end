package com.lpc.labbackend.config;

import com.lpc.labbackend.dao.ExceptionRecordMapper;
import com.lpc.labbackend.entity.CustomizedException;
import com.lpc.labbackend.entity.ExceptionRecord;
import com.lpc.labbackend.entity.ResponseData;
import com.lpc.labbackend.enumeration.HttpStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * 全局的异常处理器
 */
@RestControllerAdvice
public class CustomizedExceptionHandler {
    @Autowired
    private ExceptionRecordMapper exceptionRecordMapper;

    private static final Logger logger
            = LoggerFactory.getLogger(CustomizedExceptionHandler.class);

    /**
     * 捕获自定义的异常
     */
    @ExceptionHandler(value = CustomizedException.class)
    public ResponseData customiezdExceptionHandle(CustomizedException ex) {
        //打印错误信息
        logger.error(ex.getMsg());
        //把异常存入数据库方便查看
        exceptionRecordMapper.insertException(
                new ExceptionRecord(ex.getCode(), ex.getMsg(), new Date(System.currentTimeMillis())));

        ResponseData responseData = new ResponseData(ex.getCode(),
                ex.getMsg(),
                null);
        return responseData;
    }

    /**
     * 捕获Java的异常类
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseData customiezdExceptionHandle(Exception ex) {
        //打印错误信息
        logger.error(ex.getMessage());
        //把异常存入数据库方便查看
        exceptionRecordMapper.insertException(
                new ExceptionRecord(HttpStatusEnum.INTERNAL_SERVER_ERROR.getCode(),
                        ex.getMessage(),
                        new Date(System.currentTimeMillis())));
        ResponseData responseData = new ResponseData(HttpStatusEnum.INTERNAL_SERVER_ERROR.getCode(),
                ex.getMessage(),
                null);
        return responseData;
    }
}
