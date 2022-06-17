package com.zy.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.yygh.model.user.UserInfo;
import com.zy.yygh.vo.user.LoginVo;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {

    ////QQ邮箱登录接口
    Map<String, Object> loginUser(LoginVo loginVo);

    //根据openid判断
    UserInfo selectWxInfoOpenId(String openid);
}
