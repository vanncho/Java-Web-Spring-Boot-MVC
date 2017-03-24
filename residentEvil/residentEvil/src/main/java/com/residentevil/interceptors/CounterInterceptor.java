package com.residentevil.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CounterInterceptor extends HandlerInterceptorAdapter {

    private long count;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        this.count++;
        request.setAttribute("count", this.count);
        return true;
    }
}
