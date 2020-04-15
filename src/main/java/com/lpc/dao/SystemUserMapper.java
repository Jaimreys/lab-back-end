package com.lpc.dao;

import com.lpc.entity.dto.StudentDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemUserMapper {
    List<StudentDTO> selectStudents(String realName);
}