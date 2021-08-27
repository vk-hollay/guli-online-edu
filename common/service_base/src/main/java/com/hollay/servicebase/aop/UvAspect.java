package com.hollay.servicebase.aop;

import com.hollay.commonutils.JwtUtils;
import com.hollay.commonutils.R;
import com.hollay.servicebase.annotation.MyUvLogAop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 关于网站访问量UV统计
 * UV：独立访问用户数：即UniqueVisitor，访问网站的一台电脑客户端为一个访客。00:00-24:00内相同的客户端只被计算一次。
 */

@Aspect
@Component
public class UvAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 拿到 @MyUvLogAop 注解注释的方法，这里就是切点
    @Pointcut("@annotation(com.hollay.servicebase.annotation.MyUvLogAop)")
    private void pointCut(){
    }

    @After("pointCut()")
    public void doAfter() {
        System.out.println("*****************aop织入******************");
    }

    /**
     * 在目标方法正常完成后被织入
     * @param joinPoint
     * @param returnVal
     */
    @AfterReturning(returning = "returnVal", pointcut = "pointCut() && @annotation(myUvLogAop)")
    public void doAfterReturning(JoinPoint joinPoint, R returnVal, MyUvLogAop myUvLogAop) {
        String value = myUvLogAop.value();//获取自定义注解 @MyUvLogAop的参数值
        if ("login".equals(value)) {
            countLoginUser(returnVal);
        } else if ("play_video".equals(value)) {
            countPlayVideoNum();
        }
    }

    //统计每日登录用户数，存进redis的Set集合中
    private void countLoginUser(R returnVal) {
        //获取成功登录的token以及用户id
        Map<String, Object> data = returnVal.getData();
        String token = data.get("token").toString();
        String memberId = JwtUtils.getMemberIdByJwtToken(token);
        //将用户id添加到redis的set集合中，用于统计每日登录用户数
        String redisKey = "PerDayloginNum::" + new DateTime().toString("yyyy-MM-dd");  //PerDayloginNum::2021-08-22
        stringRedisTemplate.opsForSet().add(redisKey, memberId);
        System.out.println("*****************成功登录：" + returnVal);
    }

    //统计每日视频播放总数
    private void countPlayVideoNum() {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String redisKey = "PerDayPlayVideoNum::" + new DateTime().toString("yyyy-MM-dd");  //PerDayPlayVideoNum::2021-08-22
        //key对应的value值每次自增1
        valueOperations.increment(redisKey);
        System.out.println("*****************播放视频");
    }
}
