package com.lpc.dao;

import com.lpc.entity.ExceptionRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionRecordMapper {
    public void insertException(ExceptionRecord exceptionRecord);
}