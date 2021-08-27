package com.hollay.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 内容发布管理服务模块
 */

@EnableDiscoveryClient //nacos注册
@SpringBootApplication
@ComponentScan({"com.hollay"}) //设置包扫描规则为com.hollay，common中的配置（如swagger）才能加载得到
@MapperScan("com.hollay.educms.mapper")
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
