package com.zy.yygh.hosp.controller.api;

import com.zy.yygh.common.exception.YyghException;
import com.zy.yygh.common.helper.HttpRequestHelper;
import com.zy.yygh.common.result.Result;
import com.zy.yygh.common.result.ResultCodeEnum;
import com.zy.yygh.common.utils.MD5;
import com.zy.yygh.hosp.service.DepartmentService;
import com.zy.yygh.hosp.service.HospitalService;
import com.zy.yygh.hosp.service.HospitalSetService;
import com.zy.yygh.hosp.service.ScheduleService;
import com.zy.yygh.model.hosp.Department;
import com.zy.yygh.model.hosp.Hospital;
import com.zy.yygh.model.hosp.Schedule;
import com.zy.yygh.vo.hosp.DepartmentQueryVo;
import com.zy.yygh.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "医院管理API接口")
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    //删除排班接口
    @ApiOperation(value = "删除排班接口")
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request){
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = paramMap.get("hoscode").toString();
        //排班编号
        String hosScheduleId = paramMap.get("hosScheduleId").toString();

        //1.获取医院系统传递过来的签名，签名进行MD5加密
        extracted(paramMap, hoscode);

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();

    }

    //查询排班接口
    @ApiOperation(value = "查询排班接口")
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request){
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //医院编号
        String hoscode = paramMap.get("hoscode").toString();
        //科室编号
        String depcode = (String) paramMap.get("depcode");
        //当前页 和每页的记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt(paramMap.get("page").toString());
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt(paramMap.get("limit").toString());
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        extracted(paramMap, hoscode);

        ScheduleQueryVo scheduleQueryVo=new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);

        //调用service方法
        Page<Schedule> pageModel =scheduleService.findPageSchedule(page,limit,scheduleQueryVo);
        return Result.ok(pageModel);
    }

    //上传排班接口
    @ApiOperation(value = "上传排班接口")
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = paramMap.get("hoscode").toString();
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        extracted(paramMap, hoscode);
        scheduleService.save(paramMap);
        return Result.ok();
    }

    //删除科室的接口
    @ApiOperation(value = "删除科室的接口")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request){
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //医院编号
        String hoscode = paramMap.get("hoscode").toString();
        //科室编号
        String depcode = paramMap.get("depcode").toString();
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        extracted(paramMap, hoscode);
        departmentService.remove(hoscode,depcode);
        return Result.ok();
    }

    //查询科室的接口
    @ApiOperation(value = "查询科室的接口")
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request) {
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = paramMap.get("hoscode").toString();
        //当前页 和每页的记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt(paramMap.get("page").toString());
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt(paramMap.get("limit").toString());
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        extracted(paramMap, hoscode);

        DepartmentQueryVo departmentQueryVo=new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        //调用service方法
        Page<Department> pageModel =departmentService.findPageDepartment(page,limit,departmentQueryVo);
        return Result.ok(pageModel);
    }



    //上传科室接口
    @ApiOperation(value = "上传科室接口")
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //获取医院编号
        String hoscode = paramMap.get("hoscode").toString();
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        extracted(paramMap, hoscode);
        //调用service方法实现上传科室接口
        departmentService.save(paramMap);
        return Result.ok();
    }


    //查询医院接口
    @ApiOperation(value = "查询医院接口")
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request) {
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = paramMap.get("hoscode").toString();
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        extracted(paramMap, hoscode);

        //调用service方法实现根据医院编号查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }

    //上传医院接口
    @ApiOperation(value = "上传医院接口")
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = paramMap.get("hoscode").toString();
        extracted(paramMap, hoscode);

        //5 传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoDataString = (String) paramMap.get("logoData");
        String logoData = logoDataString.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);

        //调用service的方法
        hospitalService.save(paramMap);
        return Result.ok();
    }

    //抽取的代码
    private void extracted(Map<String, Object> paramMap, String hoscode) {
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        Object hospSign = paramMap.get("sign");

        //2 根据传递过来的医院编号，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);

        //3 把数据库查询的签名进行MD5加密
        String signKeyMD5 = MD5.encrypt(signKey);

        //4 判断签名是否一致
        if (!hospSign.equals(signKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
    }
}
