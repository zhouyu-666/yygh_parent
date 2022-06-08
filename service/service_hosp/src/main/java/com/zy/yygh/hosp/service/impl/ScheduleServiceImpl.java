package com.zy.yygh.hosp.service.impl;

import com.zy.yygh.hosp.repository.ScheduleRepository;
import com.zy.yygh.hosp.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

}
