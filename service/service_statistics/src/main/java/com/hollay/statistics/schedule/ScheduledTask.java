package com.hollay.statistics.schedule;

import com.hollay.statistics.service.StatisticsDailyService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService staService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

/*
    // cron  七子（七域）表达式  springboot整合定时任务cron表达式只支持6位，第七位年默认当前年。
    // 0/5 * * * * ?表示每隔5秒执行一次这个方法
    @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        System.out.println("**************task1执行了..");
    }

*/
    //在每天凌晨1点，把前一天数据进行数据统计查询，并添加到数据库的统计表中
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        // Joda-Time类， 获取当前日期的前一天
        String dateTime = new DateTime().minusDays(1).toString("yyyy-MM-dd");
        staService.statisticsDailyCount(dateTime);
    }


    //在每天凌晨1点，把存储在redis中的一周前的统计数据进行删除
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeRedisDataTask() {
        // Joda-Time类， 获取当前日期的前7天
        String dateTime = new DateTime().minusDays(7).toString("yyyy-MM-dd");
        String redisKey = "PerDayloginNum::" + dateTime;
        //设置失效
        stringRedisTemplate.expire(redisKey, -2, TimeUnit.SECONDS);
    }
}
