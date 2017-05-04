package com.onlinebusreservation.configuration;

import com.onlinebusreservation.interceptors.UserUnreadMessagesInterceptor;
import com.onlinebusreservation.interceptors.ValidBookingCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    private final ValidBookingCheckInterceptor bookingCheckInterceptor;

    private final UserUnreadMessagesInterceptor userUnreadMessagesInterceptor;

    @Autowired
    public WebMvcConfiguration(ValidBookingCheckInterceptor bookingCheckInterceptor,
                               UserUnreadMessagesInterceptor userUnreadMessagesInterceptor) {

        this.bookingCheckInterceptor = bookingCheckInterceptor;
        this.userUnreadMessagesInterceptor = userUnreadMessagesInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(this.bookingCheckInterceptor).addPathPatterns("/booking/*");
        registry.addInterceptor(this.userUnreadMessagesInterceptor);
    }
}
