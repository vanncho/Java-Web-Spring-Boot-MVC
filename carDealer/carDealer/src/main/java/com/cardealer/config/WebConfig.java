package com.cardealer.config;

import com.cardealer.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean(name = "loginFilter")
    public Filter loginFilter() {
        return new LoginFilter();
    }

    @Bean
    public FilterRegistrationBean helloFilterRegistrationBean() {

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        registrationBean.addUrlPatterns("/cars/add","/parts/add","/parts/edit/*","/sales/add","/logs/*");
        registrationBean.setFilter(loginFilter());

        return registrationBean;
    }
}