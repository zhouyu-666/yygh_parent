package com.zy.yygh.msm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.zy.yygh.msm.service.MsmService;
import com.zy.yygh.msm.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String phone, String code) {
        //判断手机是否为空
        if (StringUtils.isEmpty(phone)){
            return false;
        }
        //整合容联云通讯
        //设置相关参数
        HashMap<String, Object> result = null;
        //初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        //初始化服务器地址和端口
        restAPI.init("app.cloopen.com", "8883");
        //初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN
        restAPI.setAccount(ConstantPropertiesUtils.ACCOUNT_SID, ConstantPropertiesUtils.AUTH_TOKEN);
        //初始化应用ID
        restAPI.setAppId(ConstantPropertiesUtils.APP_ID);
        //验证码用json格式
//        Map<String,Object> param=new HashMap<>();
//        param.put("code",code);
        //调用发送模板短信的接口发送短信
        result = restAPI.sendTemplateSMS(phone,"1" ,new String[]{code,"2"});
        System.out.println("SDKTestGetSubAccounts result=" + result);

        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
            return true;
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            return false;
        }
    }
}
