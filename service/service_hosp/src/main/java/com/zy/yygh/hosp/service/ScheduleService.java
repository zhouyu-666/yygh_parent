package com.zy.yygh.hosp.service;

import com.zy.yygh.model.hosp.Schedule;
import com.zy.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    //上传排班接口
    void save(Map<String, Object> paramMap);

    //查询排班接口
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    //删除排班接口
    void remove(String hoscode, String hosScheduleId);

    //查询排班的规则数据
    Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);

    //根据医院的编号 、科室的编号和工作日期，查询出排班的详情信息
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);
}
