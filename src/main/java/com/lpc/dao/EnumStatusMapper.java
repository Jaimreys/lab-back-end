package com.lpc.dao;

import com.lpc.entity.pojo.EnumStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnumStatusMapper {
   List<EnumStatus> selectStatus();
}