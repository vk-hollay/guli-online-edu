package com.hollay.eduservice.controller;


import com.hollay.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin //解决跨域问题
public class EduLoginController {

    //登录
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    //登录用户信息
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://my-guli-edu-oss.oss-cn-beijing.aliyuncs.com/2021/07/17/7ebbd02154534bc5b66421898a9e271dfile.png");
    }
}
