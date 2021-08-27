package com.hollay.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 教育服务模块
 */

@EnableDiscoveryClient //nacos注册
@EnableFeignClients //用于服务调用
@SpringBootApplication
//@MapperScan("com.hollay.eduservice.mapper") //该注解可写在config类中
@ComponentScan(basePackages = {"com.hollay"}) //设置包扫描规则为com.hollay，common中的配置（如swagger）才能加载得到
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
