package com.zy.yygh.msm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {
    //主账号ID
    @Value("${cloopen.sms.accountSid}")
    private String accountSid;

    //账号授权令牌
    @Value("${cloopen.sms.authToken}")
    private String authToken;

    //AppID
    @Value("${cloopen.sms.appId}")
    private String appId;

    public static String ACCOUNT_SID;
    public static String AUTH_TOKEN;
    public static String APP_ID;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCOUNT_SID=accountSid;
        AUTH_TOKEN=authToken;
        APP_ID=appId;
    }
}
