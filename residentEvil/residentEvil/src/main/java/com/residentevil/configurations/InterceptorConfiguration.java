package com.residentevil.configurations;

import com.residentevil.interceptors.CounterInterceptor;
import com.residentevil.interceptors.IpInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new IpInterceptor()).addPathPatterns("/").addPathPatterns("/home");
        registry.addInterceptor(new CounterInterceptor()).addPathPatterns("/").addPathPatterns("/home");
    }
}
