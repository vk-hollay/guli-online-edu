package com.hollay.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 全局配置处理跨域问题
 *
 * 有了这个配置类后就不用写 @CrossOrigin注解了 （也不能再写该注解，同时存在会出现错误，即该类和@CrossOrigin注解只能二选一）
 */


/*

@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsFilter() {
        //1. 添加 CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //放行哪些请求方式
        config.addAllowedMethod("*");
        //放行哪些原始域
        config.addAllowedOrigin("*");
        //放行哪些原始请求头部信息
        config.addAllowedHeader("*");
        //是否发送 Cookie
        //config.setAllowCredentials(true);
        //2. 添加映射路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        //3. 返回新的CorsFilter
        return new CorsWebFilter(source);
    }
}

*/
