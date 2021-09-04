package com.hollay.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hollay.commonutils.JwtUtils;
import com.hollay.commonutils.MD5;
import com.hollay.servicebase.exception.MyException;
import com.hollay.ucenter.entity.Member;
import com.hollay.ucenter.entity.Vo.RegisterVo;
import com.hollay.ucenter.mapper.MemberMapper;
import com.hollay.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //登录
    @Override
    public String login(Member member) {
        //手机号和密码做登录
        //1、获取手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //2、如过手机号和密码，直接返回登录失败
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new MyException(20001, "手机号和密码为空");
        }
        //判断手机号是否正确
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Member mobileMember = baseMapper.selectOne(queryWrapper);
        if (mobileMember == null){
            throw new MyException(20001,"手机号不存在");
        }

        //判断密码是否相等
        //数据库密码进行加密，不能直接对比
        //MD5对密码进行加密，再与数据库进行比较
        String password1 = mobileMember.getPassword();
        if (!MD5.encrypt(password).equals(password1)){
            throw new MyException(20001,"密码错误");
        }

        //判断用户是否被禁用
        if(mobileMember.getIsDisabled()){
            throw new MyException(20001,"用户被禁用登录失败");
        }

        //登录成功
        //按照jwt生产token返回
        String token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return token;
    }

    //注册
    @Override
    public void register(RegisterVo registerVo, String ip) {
        //获取注册基本信息
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        //判断是否为空
        if (StringUtils.isEmpty(code)||StringUtils.isEmpty(mobile)||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(password)){
            throw new MyException(20001,"注册失败");
        }

        //判断验证码
        String redisCode = stringRedisTemplate.opsForValue().get(ip);
        System.out.println(ip);
        System.out.println("redisCode:"+redisCode);
        System.out.println("code:"+code);
        if (!code.equalsIgnoreCase(redisCode)){
            throw new MyException(20001,"验证码错误");
        }

        //判断手机号在数据库中是否存在
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            throw new MyException(20001,"注册失败");
        }

        //将数据添加到数据库
        Member ucenterMember = new Member();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);//用户不禁用
        ucenterMember.setAvatar("https://edu-longyang.oss-cn-beijing.aliyuncs.com/fa104ef58c4e5bc4270d911da1d1b34d.jpg");
        baseMapper.insert(ucenterMember);
    }

    //根据微信openid查用户
    @Override
    public Member getMenberByOperid(String openid) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
