package com.cardealer.filter;

import com.cardealer.models.binding.user.UserLoginModel;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpSession httpSession = ((HttpServletRequest) servletRequest).getSession();
        UserLoginModel loggedUser = (UserLoginModel) httpSession.getAttribute("loggedUser");

        if (loggedUser == null) {
            ((HttpServletResponse) servletResponse).sendRedirect("/user/login");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
