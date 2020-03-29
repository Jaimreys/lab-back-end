package com.lpc.dao;

import com.lpc.entity.pojo.ExceptionRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionRecordMapper {
    void insertException(ExceptionRecord exceptionRecord);
}