package com.onlinebusreservation.interceptors;

import com.onlinebusreservation.areas.user.entities.User;
import com.onlinebusreservation.areas.user.repositories.AbstractUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserUnreadMessagesInterceptor extends HandlerInterceptorAdapter {

    private final AbstractUserRepository userRepository;

    @Autowired
    public UserUnreadMessagesInterceptor(AbstractUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = null;

        try {

            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        } catch (ClassCastException | NullPointerException e) {
            return true;
        }

        if (user != null) {

            int unreadMessages = this.userRepository.getUnreadMessages(user.getId());
            user.setUnreadMessages(unreadMessages);
        }

        return true;
    }
}
