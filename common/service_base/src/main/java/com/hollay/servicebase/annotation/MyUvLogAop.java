package com.hollay.servicebase.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，用于作标注aop切点位置使用
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface MyUvLogAop {
    String value() default "";
}
