package com.example.tea.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 1. 创建 CORS 配置对象
        CorsConfiguration config = new CorsConfiguration();
        // 允许的源（生产环境替换为具体前端域名，如 https://www.xxx.com）
        config.addAllowedOriginPattern("*");
        // 允许携带 Cookie（前后端分离带 Token 时需开启）
        config.setAllowCredentials(true);
        // 允许的请求方法（GET/POST/PUT/DELETE 等）
        config.addAllowedMethod("*");
        // 允许的请求头（如 Authorization/Content-Type 等）
        config.addAllowedHeader("*");
        // 预检请求有效期（秒），避免频繁发 OPTIONS 请求
        config.setMaxAge(3600L);

        // 2. 配置生效路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 所有接口都生效
        source.registerCorsConfiguration("/**", config);

        // 3. 返回过滤器
        return new CorsFilter(source);
    }
}
