package com.imyuanxiao.yuanapiadmin.config;

import com.imyuanxiao.yuanapiadmin.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description 配置拦截器
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/8/18 22:59
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .excludePathPatterns("/auth/**");
    }
}
