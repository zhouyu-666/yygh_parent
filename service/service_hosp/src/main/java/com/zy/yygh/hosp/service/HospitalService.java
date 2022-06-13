package com.zy.yygh.hosp.service;

import com.zy.yygh.model.hosp.Hospital;
import com.zy.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface HospitalService {
    //上传医院接口
    void save(Map<String, Object> paramMap);

    //查询医院的编号
    Hospital getByHoscode(String hoscode);

    //医院列表方法(条件查询分页)
    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    //更新医院上线状态
    void updateStatus(String id, Integer status);

    //医院的详情信息
    Map<String, Object> getHospById(String id);

    //获取医院名称
    String getHospName(String hoscode);
}
