package com.hollay.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 权限控制服务模块
 */
@SpringBootApplication
@EnableDiscoveryClient //nacos注册
@ComponentScan("com.hollay")
@MapperScan("com.hollay.aclservice.mapper")
public class AclApplication {

    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }

}
