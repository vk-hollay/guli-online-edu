package com.hollay.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 阿里云oss服务模块
 */

//nacos注册
@EnableDiscoveryClient
//该模块无需用到数据源，将它exclude
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//设置包扫描规则为com.hollay，common中的配置（如swagger）才能加载得到
@ComponentScan(basePackages = {"com.hollay"})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
