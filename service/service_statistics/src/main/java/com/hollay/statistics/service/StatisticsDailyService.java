package com.hollay.statistics.service;

import com.hollay.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void statisticsDailyCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
