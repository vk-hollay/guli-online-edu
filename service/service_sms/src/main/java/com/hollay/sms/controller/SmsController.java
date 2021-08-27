package com.hollay.sms.controller;

import com.hollay.servicebase.exception.MyException;
import com.hollay.sms.entity.VerifyCode;
import com.hollay.sms.service.SmsService;
import com.hollay.sms.utils.VerifyCodeGenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/edusms/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    //生成页面验证码
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置长宽，生成验证码
            VerifyCode verifyCode = VerifyCodeGenUtils.generate(95, 38);

            String code = verifyCode.getCode();
            //System.out.println("code = " + code);
            //将VerifyCode绑定session，用来验证
            //request.getSession().setAttribute("VerifyCode", code);
            //存储到redis，用来验证
            smsService.storeToRedis(request.getRemoteAddr(), code);

            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new MyException(20001, "验证码生成异常");
        }
    }

}
