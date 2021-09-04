package com.hollay.ucenter.controller;

import com.hollay.commonutils.JwtUtils;
import com.hollay.commonutils.R;
import com.hollay.servicebase.annotation.MyUvLogAop;
import com.hollay.ucenter.entity.Member;
import com.hollay.ucenter.entity.Vo.RegisterVo;
import com.hollay.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/educenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    //前台登录   电话和密码登录
    @MyUvLogAop("login")
    @PostMapping("login")
    public R login(@RequestBody Member member){
        //返回token值，使用jwt生成
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    //前台注册  电话、密码、验证码、昵称进行注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo, HttpServletRequest request){
        memberService.register(registerVo, request.getRemoteAddr());
        return R.ok();
    }

    //获取用户信息
    //根据token获取用户id，再通过用户id查询数据库获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类，获取头部信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据id获得用户信息
        Member member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    //根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public Map<String, Object> getUserInfoOrder(@PathVariable String id) {
        Member member = memberService.getById(id);
        if (member != null) {
            BeanMap beanMap = BeanMap.create(member);
            System.out.println(beanMap);
            return beanMap;
        }
        return null;
    }

    //查询某一天注册人数
    @GetMapping("countRegister/{day}")
    public Integer countRegister(@PathVariable String day) {
        return memberService.countRegisterDay(day);
    }


}

