package com.hollay.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 统计分析服务模块
 */

@EnableScheduling //开启定时任务
@EnableDiscoveryClient //nacos注册
@EnableFeignClients //服务调用
@SpringBootApplication
@ComponentScan(basePackages = {"com.hollay"})
@MapperScan("com.hollay.statistics.mapper")
public class StaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}
