package com.lpc.service.impl;

import com.lpc.dao.EnumStatusMapper;
import com.lpc.dao.StudentStatusRecordMapper;
import com.lpc.dao.SystemUserMapper;
import com.lpc.entity.dto.StatusStatisticsDTO;
import com.lpc.entity.dto.StudentDTO;
import com.lpc.entity.pojo.EnumStatus;
import com.lpc.entity.pojo.StatusStatistics;
import com.lpc.entity.pojo.StudentStatusRecord;
import com.lpc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    //定义常量
    private final Date ZERO_TIME = new Date(0);
    private final Instant ZERO_INSTANT = Instant.ofEpochMilli(0);
    private final long SECONDS_IN_DAY = 24 * 60 * 60;
    private final long MILLIS_IN_EIGHT_HOURS = 8 * 60 * 60 * 1000;

    private SystemUserMapper systemUserMapper;
    private StudentStatusRecordMapper studentStatusRecordMapper;
    private EnumStatusMapper enumStatusMapper;

    @Autowired
    public StudentServiceImpl(SystemUserMapper systemUserMapper,
                              StudentStatusRecordMapper studentStatusRecordMapper,
                              EnumStatusMapper enumStatusMapper) {
        this.systemUserMapper = systemUserMapper;
        this.studentStatusRecordMapper = studentStatusRecordMapper;
        this.enumStatusMapper = enumStatusMapper;
    }

    /**
     * 获取所有学生的信息
     */
    @Override
    public List<StudentDTO> getStudents(String realName) {
        return systemUserMapper.selectStudents(realName);
    }

    /**
     * todo 这里还要考虑到当前月还没到的那几天，以及当前天还没到的那几个小时
     */
    @Override
    public StatusStatisticsDTO[] getStudentStatusMonthly(Long username, Calendar start) {
        // 月份0-11
        // 生成统计的结束时间，期限为一个月
        Calendar end = (Calendar) start.clone();
        end.add(Calendar.MONTH, 1);

        // 一次性获取所有数据，然后通过stream在Java处理
        List<StudentStatusRecord> studentStatusRecords = studentStatusRecordMapper.selectStudentStatusMonthly(username,
                start.getTime(),
                end.getTime());

        //获取当前数据库里有的所有状态
        List<EnumStatus> statusList = enumStatusMapper.selectStatus();
        int statusNum = statusList.size();
        //将状态转为map
        Map<Integer, String> statusMap = statusList.stream()
                .collect(Collectors.toMap(EnumStatus::getId, EnumStatus::getStatus));

        // 计算当前月有几天
        int daysInMonth = start.getActualMaximum(Calendar.DATE);
        List<StatusStatistics>[] resultByDay = new List[daysInMonth];

        //聚合同一天的所有个状态
        Map<Date, List<StudentStatusRecord>> mapByDate = studentStatusRecords.stream()
                .collect(Collectors.groupingBy(StudentStatusRecord::getStatusStartDate));
        mapByDate.forEach((dateKey, dateValue) -> {
            //这个list里包含的是同一天中的各个状态
            List<StatusStatistics> statusStatisticsList = new ArrayList<>(statusNum);
            //聚合每种状态
            Map<Integer, List<StudentStatusRecord>> mapByStatus = dateValue.stream()
                    .collect(Collectors.groupingBy(StudentStatusRecord::getStatusId));
            mapByStatus.forEach((statusKey, statusValue) -> {
                //根据状态将持续时间求和
                long sumMillis = 0;
                for (StudentStatusRecord record : statusValue) {
                    long time = record.getStatusDuration().getTime() + MILLIS_IN_EIGHT_HOURS;
                    sumMillis += time;
                }
                Date sumDate = new Date(sumMillis);

                //形成一条统计信息
                StatusStatistics statusStatistics = new StatusStatistics();
                statusStatistics.setStatusId(statusKey);
                statusStatistics.setStatus(statusMap.get(statusKey));
                //计算持续时间
                Instant endInstant = Instant.ofEpochMilli(sumDate.getTime());
                Duration duration = Duration.between(ZERO_INSTANT, endInstant);
                statusStatistics.setDuration(Long.valueOf(duration.getSeconds()).intValue());
                //计算比例
                BigDecimal proportion = BigDecimal.valueOf(
                        duration.getSeconds()).divide(BigDecimal.valueOf(SECONDS_IN_DAY),
                        4,
                        BigDecimal.ROUND_HALF_UP);
                statusStatistics.setProportion(proportion);
                //添加到数组中
                // todo 没有的状态需不需要添加？
                statusStatisticsList.add(statusStatistics);
            });
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateKey);
            int index = calendar.get(Calendar.DAY_OF_MONTH) - 1;
            resultByDay[index] = statusStatisticsList;
        });

        //todo 后台需要调整一下返回数据
        //返回一个数组，数组长度是状态的数量，每个元素包含状态id,状态名称，还有一个数组，包含每天的状态秒数（int）
        StatusStatisticsDTO[] resultByStatus = new StatusStatisticsDTO[statusNum];
        for (int i = 0; i < statusNum; i++) {
            Integer[] dailyDuration = new Integer[daysInMonth];
            //j表示一个月的天数，如一号，二号，三号
            for (int j = 0; j < daysInMonth; j++) {
                //如果是空的，就设为0
                if (resultByDay[j] == null) {
                    dailyDuration[j] = 0;
                } else {
                    for (StatusStatistics statusStatistics : resultByDay[j]) {
                        //如果这一天有这种状态的时间
                        if (statusList.get(i).getId().equals(statusStatistics.getStatusId())) {
                            dailyDuration[j] = statusStatistics.getDuration();
                        }
                    }
                    //如果循环结束还是空的，就赋值为0
                    if (dailyDuration[j] == null) {
                        dailyDuration[j] = 0;
                    }
                }
            }
            StatusStatisticsDTO dto = new StatusStatisticsDTO();
            dto.setName(statusList.get(i).getStatus());
            dto.setData(dailyDuration);
            resultByStatus[i] = dto;
        }

        return resultByStatus;
    }
}
