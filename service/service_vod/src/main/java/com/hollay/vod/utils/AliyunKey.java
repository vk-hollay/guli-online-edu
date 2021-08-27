package com.hollay.vod.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 通过@ConfigurationProperties(prefix = "aliyun.vod.file")绑定application.properties中自定义参数，需注意以下两点：
 * （1）属性变量名称，需要跟配置中的名称一致
 * （2）需要提供getter和setter
 */
@Component
@ConfigurationProperties(prefix = "aliyun.vod.file")
@Data
public class AliyunKey implements InitializingBean {

    private String keyid;
    private String keysecret;

    public static String accessKeyId;
    public static String accessKeySecret;

    @Override
    public void afterPropertiesSet() throws Exception {
        AliyunKey.accessKeyId = this.keyid;
        AliyunKey.accessKeySecret = this.keysecret;
    }
}
