package com.hollay.sms.service.impl;

import com.hollay.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //将验证码存储到 redis
    @Override
    public void storeToRedis(String ip, String code) {
        stringRedisTemplate.opsForValue().set(ip, code);
        stringRedisTemplate.expire(ip, 60, TimeUnit.SECONDS);
    }
}
