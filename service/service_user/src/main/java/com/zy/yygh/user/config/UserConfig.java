package com.zy.yygh.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.zy.yygh.user.mapper")
public class UserConfig {
}
