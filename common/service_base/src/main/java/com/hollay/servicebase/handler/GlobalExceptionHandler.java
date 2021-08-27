package com.hollay.servicebase.handler;

import com.hollay.commonutils.ExceptionUtils;
import com.hollay.commonutils.R;
import com.hollay.servicebase.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class) //指定出现什么异常时执行这个方法
    @ResponseBody //为了返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理...");
    }

    /**
     * 特定异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class) //指定出现什么异常时执行这个方法
    @ResponseBody //为了返回数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理...");
    }

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class) //指定出现什么异常时执行这个方法
    @ResponseBody //为了返回数据
    public R error(MyException e) {
        //通过slf4j的log.error方法把异常信息输出到log_error.log文件中
        //log.error(e.getMessage());
        log.error(ExceptionUtils.getMessage(e));
        //e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
