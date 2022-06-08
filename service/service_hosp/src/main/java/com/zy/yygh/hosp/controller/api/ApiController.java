package com.zy.yygh.hosp.controller.api;

import com.zy.yygh.common.helper.HttpRequestHelper;
import com.zy.yygh.common.result.Result;
import com.zy.yygh.hosp.service.HospitalService;
import com.zy.yygh.hosp.service.HospitalSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    //上传医院接口
    @ApiOperation(value = "上传医院接口")
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request){
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //1.获取医院系统传递过来的签名，签名进行MD5加密
        Object hospSign = paramMap.get("sign");

        //2 根据传递过来的医院编号，查询数据库，查询签名
        String hoscode = paramMap.get("hoscode").toString();



        //调用service的方法
        hospitalService.save(paramMap);
        return Result.ok();
    }

}
