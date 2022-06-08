package com.zy.yygh.hosp.service;

import com.zy.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {
    //上传医院接口
    void save(Map<String, Object> paramMap);
    //查询医院的编号
    Hospital getByHoscode(String hoscode);
}
