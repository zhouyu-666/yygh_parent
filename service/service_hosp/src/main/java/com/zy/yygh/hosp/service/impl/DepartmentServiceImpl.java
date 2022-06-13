package com.zy.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zy.yygh.hosp.repository.DepartmentRepository;
import com.zy.yygh.hosp.service.DepartmentService;
import com.zy.yygh.model.hosp.Department;
import com.zy.yygh.vo.hosp.DepartmentQueryVo;
import com.zy.yygh.vo.hosp.DepartmentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    //上传科室
    @Override
    public void save(Map<String, Object> paramMap) {
        //paramMap转换成department对象
        String paramMapSpring = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(paramMapSpring, Department.class);

        //根据医院编号 和 科室编号查询
        Department departmentExist = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());
        //判断
        if (departmentExist != null) {
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);
        }else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }
    //查询科室
    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        //创建Pageable对象，设置当前页和每页记录数
        // 0是第一页
        Pageable pageable= PageRequest.of(page-1,limit);
        //创建Example对象
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo,department);
        department.setIsDeleted(0);
        ExampleMatcher matcher=ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Department> example=Example.of(department,matcher);
        Page<Department> all = departmentRepository.findAll(example, pageable);
        return all;
    }

    //删除科室
    @Override
    public void remove(String hoscode, String depcode) {
        //根据医院编号和科室编号查询
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (department!=null){
            //删除
            departmentRepository.deleteById(department.getId());
        }
    }

    //根据医院编号，查询医院所有科室列表
    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        //创建一个list集合，用于最终数据的封装
        List<DepartmentVo> result=new ArrayList<>();

        //根据医院编号，查询所有的科室信息
        Department departmentQuery=new Department();
        departmentQuery.setHoscode(hoscode);
        Example example = Example.of(departmentQuery);
        //所有的科室列表信息
        List<Department> departmentList = departmentRepository.findAll(example);

        //根据大科室编号分组 ， bigCode 分组，获取每个大科室里面的下级子科室
        Map<String, List<Department>> departmentMap =
                departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));
        //遍历departmentMap集合
        for (Map.Entry<String,List<Department>> entry : departmentMap.entrySet()){
            //大科室的编号
            String bigCode = entry.getKey();
            //大科室编号对应的全局数据
            List<Department> departmentListValue = entry.getValue();

            //封装大科室
            DepartmentVo departmentVo1=new DepartmentVo();
            departmentVo1.setDepcode(bigCode);//大科室编号
            departmentVo1.setDepname(departmentListValue.get(0).getBigname());//大科室名称

            //封装小科室
            List<DepartmentVo> children= new ArrayList<>();
            for (Department department : departmentListValue) {
                DepartmentVo departmentVo2=new DepartmentVo();
                departmentVo2.setDepcode(department.getDepcode());//小科室编号
                departmentVo2.setDepname(department.getDepname());//获取小科室名称
                //封装到list集合
                children.add(departmentVo2);
            }
            //把小科室的list集合放到大科室children里面去
            departmentVo1.setChildren(children);
            //放到最终的 result 里面去
            result.add(departmentVo1);
        }
        return result;
    }

    //根据科室编号和医院编号，查询科室名称
    @Override
    public String getDepName(String hoscode, String depcode) {
        Department department=departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode,depcode);
        if(department != null) {
            return department.getDepname();
        }
        return null;
    }


}
