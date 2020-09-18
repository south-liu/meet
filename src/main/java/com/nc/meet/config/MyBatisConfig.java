package com.nc.meet.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis 配置
 * @author liulinbo
 */
@Configuration
@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
//@MapperScan(basePackages = "com.nc.meet.modules.*.mapper")
public class MyBatisConfig {
}
