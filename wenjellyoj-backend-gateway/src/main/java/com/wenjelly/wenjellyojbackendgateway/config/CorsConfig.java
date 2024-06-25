package com.wenjelly.wenjellyojbackendgateway.config;

/*
 * @time 2024/6/20 10:25
 * @package com.wenjelly.wenjellyojbackendgateway.config
 * @project wenjellyoj-backend-microservice
 * @author WenJelly
 */



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;

/**
 * 处理跨域
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsWebFilter() {

        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        // 是否允许证书（cookies）
        config.setAllowCredentials(true);
        // todo 修改问本地地址 / 线上地址
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

}
