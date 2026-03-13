package com.example.tea.Config;

import com.example.tea.interceptor.AdminInterceptor;
import com.example.tea.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private UserInterceptor userInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. 注册用户拦截器（先执行）
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/api/user/**") // 拦截所有/api开头的接口
                .excludePathPatterns(       // 放行公开接口
                        "/api/user/register/**",
                        "/api/user/login/**",
                        "/api/user/findAllGoods/**",
                        "/api/user/findGoodById/**",
                        "/api/user/captcha/**",
                        "/api/upload"
                );

        // 2. 注册管理员拦截器（后执行，仅拦截后台管理接口）
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/admin/**"); // 仅拦截/admin开头的后台接口
    }
}
