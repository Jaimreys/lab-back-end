package com.lpc.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lpc.dao.StudentMapperPlusUtil;
import com.lpc.dao.StudentStateRecordMapperPlus;
import com.lpc.dao.StudentStateRecordMapperPlusUtil;
import com.lpc.dao.SystemUserMapperPlus;
import com.lpc.entity.dto.StateStatisticsDTO;
import com.lpc.entity.enumeration.StudentStateEnum;
import com.lpc.entity.pojo.StateStatistics;
import com.lpc.entity.pojo.StudentStateRecord;
import com.lpc.entity.pojo.SystemUser;
import com.lpc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentStateRecordMapperPlusUtil studentStateRecordMapperPlusUtil;
    private final StudentMapperPlusUtil studentMapperPlusUtil;
    private final SystemUserMapperPlus systemUserMapperPlus;
    private final StudentStateRecordMapperPlus studentStateRecordMapperPlus;

    @Autowired
    public StudentServiceImpl(StudentStateRecordMapperPlusUtil studentStateRecordMapperPlusUtil,
                              StudentMapperPlusUtil studentMapperPlusUtil,
                              SystemUserMapperPlus systemUserMapperPlus,
                              StudentStateRecordMapperPlus studentStateRecordMapperPlus
    ) {
        this.studentStateRecordMapperPlusUtil = studentStateRecordMapperPlusUtil;
        this.studentMapperPlusUtil = studentMapperPlusUtil;
        this.systemUserMapperPlus = systemUserMapperPlus;
        this.studentStateRecordMapperPlus = studentStateRecordMapperPlus;
    }

    /**
     * 获取所有学生
     */
    @Override
    public List<SystemUser> getAllStudents() {
        return studentMapperPlusUtil.selectAllStudents();
    }

    /**
     * 获取某个月的某个学生的状态统计信息
     * 根据前台的需要将数据转成了对应的格式
     */
    @Override
    public StateStatisticsDTO[] getStudentStateMonthly(Long username, LocalDateTime start) {
        LocalDate startDay = start.toLocalDate();
        //判断今天是不是在当前月里面，如果是，今天及本月以后的那几天可以直接不用查了
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        //现在月份的第一天
        LocalDate firstDayOfCurrentMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        //当月最后一天
        LocalDate endDay;

        if (firstDayOfCurrentMonth.isEqual(startDay)) {
            //如果查询的是当前月份，那么查询范围是本月第一天到昨天
            endDay = today.minusDays(1);
        } else {
            endDay = startDay.with(TemporalAdjusters.lastDayOfMonth());
        }

        // 一次性获取所有数据，然后通过stream在Java处理
        List<StudentStateRecord> studentStateRecords = studentStateRecordMapperPlusUtil.selectStudentStatusMonthly(username,
                startDay,
                endDay);

        //获取所有状态
        StudentStateEnum[] studentStateEnumArray = StudentStateEnum.values();
        int stateNum = studentStateEnumArray.length;
        Map<Integer, String> stateMap = Arrays.stream(studentStateEnumArray)
                .collect(Collectors.toMap(
                        StudentStateEnum::getId,
                        StudentStateEnum::getState));

        // 计算当前月有几天
        int daysInMonth = start.toLocalDate().lengthOfMonth();
        List<StateStatistics>[] resultByDay = new ArrayList[daysInMonth];

        //聚合同一天的所有个状态
        Map<LocalDate, List<StudentStateRecord>> mapByDate = studentStateRecords.stream()
                .collect(Collectors.groupingBy(StudentStateRecord::getStateStartDate));
        mapByDate.forEach((dateKey, dateValue) -> {
            //这个list里包含的是同一天中的各个状态
            List<StateStatistics> stateStatisticsList = new ArrayList<>(stateNum);
            //聚合每种状态
            Map<String, List<StudentStateRecord>> mapByState = dateValue.stream()
                    .collect(Collectors.groupingBy(StudentStateRecord::getState));
            mapByState.forEach((stateKey, stateValue) -> {
                //计算一种状态持续时间的总和
                Integer sumDuration = stateValue.stream()
                        .map(StudentStateRecord::getStateDuration)
                        .reduce(0, Integer::sum);

                //形成一条统计信息
                StateStatistics stateStatistics = new StateStatistics();
                stateStatistics.setState(stateKey);
                stateStatistics.setDuration(sumDuration);
                //添加到数组中
                stateStatisticsList.add(stateStatistics);
            });
            //将天数-1作为索引
            int index = dateKey.getDayOfMonth() - 1;
            resultByDay[index] = stateStatisticsList;
        });

        //返回一个数组，数组长度是状态的数量，每个元素包含状态id,状态名称，还有一个数组，包含每天的状态秒数（int）
        StateStatisticsDTO[] resultByState = new StateStatisticsDTO[stateNum];
        for (int i = 0; i < stateNum; i++) {
            Integer[] dailyDuration = new Integer[daysInMonth];
            //j表示一个月的天数，如一号，二号，三号
            for (int j = 0; j < daysInMonth; j++) {
                //如果是空的，就设为0
                if (resultByDay[j] == null) {
                    dailyDuration[j] = 0;
                } else {
                    for (StateStatistics stateStatistics : resultByDay[j]) {
                        //如果这一天有这种状态的时间
                        if (studentStateEnumArray[i].getState().equals(stateStatistics.getState())) {
                            dailyDuration[j] = stateStatistics.getDuration();
                        }
                    }
                    //如果循环结束还是空的，就赋值为0
                    if (dailyDuration[j] == null) {
                        dailyDuration[j] = 0;
                    }
                }
            }
            StateStatisticsDTO dto = new StateStatisticsDTO();
            dto.setName(studentStateEnumArray[i].getState());
            dto.setData(dailyDuration);
            resultByState[i] = dto;
        }

        return resultByState;
    }

    /**
     * 获取学生
     */
    @Override
    public Page<SystemUser> getStudents(int pageNum, int pageSize, String realName) {
        return studentMapperPlusUtil.selectStudents(pageNum, pageSize, realName);
    }

    // 业务逻辑还没写完
    @Transactional
    @Override
    public void updateStudentState(SystemUser systemUser) {
        systemUserMapperPlus.updateById(systemUser);
    }

    /**
     * 在每天0点的时候，重置学生的状态
     */
    @Override
    public void initStudentsState0oclockEveryday() {
        // 获取所有学生
        List<SystemUser> studentList = getAllStudents();
        for (SystemUser student : studentList) {
            if (student.getState() == null) {
                // 如果状态为空，设置为休息
                student.setState(StudentStateEnum.REST.getState());
            } else {
                // 如果状态不为空，查一查这个学生前一天的最后一个状态
                StudentStateRecord studentHesternalLastState
                        = studentStateRecordMapperPlusUtil.selectStudentHesternalLastState(student.getLongUsername());
                if (studentHesternalLastState != null) {
                    // 如果昨天最后一条状态存在的话，就计算出这个状态的持续时间
                    // 计算持续时间，结束时间是当天最后一秒
                    int duration = (int) Duration
                            .between(studentHesternalLastState.getStateStartTime(),
                                    LocalTime.of(23, 59, 59))
                            .getSeconds();
                    studentHesternalLastState.setStateDuration(duration);
                    studentStateRecordMapperPlus.updateById(studentHesternalLastState);
                }
            }
            // 插入新一天的新状态记录
            StudentStateRecord initRecord = new StudentStateRecord();
            initRecord.setUsername(student.getLongUsername());
            initRecord.setRealName(student.getRealName());
            initRecord.setState(student.getState());
            initRecord.setStateStartDate(LocalDate.now());
            initRecord.setStateStartTime(LocalTime.of(0,0,0));
            studentStateRecordMapperPlus.insert(initRecord);
            // 更新学生状态
            systemUserMapperPlus.updateById(student);
        }
    }
}
