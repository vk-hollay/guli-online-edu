package com.hollay.commonutils;

/**
 * 返回结果状态码
 */
public enum ResultCode{

    //自定义返回结果的状态码
    SUCCESS("成功", 20000),
    ERROR("失败", 20001);

    private final String name;
    private final Integer code;

    //枚举类的构造方法是私有的
    ResultCode(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }
}
