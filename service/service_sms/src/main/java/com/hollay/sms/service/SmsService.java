package com.hollay.sms.service;

public interface SmsService {

    void storeToRedis(String ip, String code);
}
