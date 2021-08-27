package com.hollay.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 */


@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor  //有参数构造器
@NoArgsConstructor   //生成无参数构造
public class MyException extends RuntimeException {
    private Integer code; //状态码
    private String msg; //输出消息
}
