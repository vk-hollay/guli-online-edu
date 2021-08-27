package com.hollay.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 订单服务模块
 */
@SpringBootApplication
@EnableDiscoveryClient //nacos注册
@EnableFeignClients //服务调用
@MapperScan("com.hollay.order.mapper")
@ComponentScan(basePackages = {"com.hollay"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
