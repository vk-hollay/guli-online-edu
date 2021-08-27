package com.hollay.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 短信服务模块
 * 由于阿里云短信服务未上线网站无法申请，故该模块改为实现生成页面验证码
 */
@EnableDiscoveryClient //nacos注册
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.hollay"})
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}
