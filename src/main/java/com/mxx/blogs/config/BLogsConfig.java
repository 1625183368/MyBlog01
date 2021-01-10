package com.mxx.blogs.config;

import com.mxx.blogs.interceptor.BLogsInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@MapperScan("com.mxx.blogs.mapper")
@PropertySource("classpath:server.properties")
public class BLogsConfig implements WebMvcConfigurer {
    @Bean
    public BLogsInterceptor bLogsInterceptor(){
        return new BLogsInterceptor() ;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bLogsInterceptor()).addPathPatterns("/**");
    }
}
