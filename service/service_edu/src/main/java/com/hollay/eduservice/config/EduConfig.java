package com.hollay.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hollay
 * @create 2021-06-23-13:44
 * @description
 */

@Configuration
@MapperScan("com.hollay.eduservice.mapper")
public class EduConfig {

    /**
     *  使用逻辑删除：
     * 1. 配置逻辑删除插件(如下代码)  2.在实体类的标记是否已删除属性上使用@TableLogic注解
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
