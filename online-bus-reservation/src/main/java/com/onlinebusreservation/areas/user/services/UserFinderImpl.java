package com.onlinebusreservation.areas.user.services;

import com.onlinebusreservation.areas.user.entities.Role;
import com.onlinebusreservation.areas.user.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserFinderImpl implements UserFinder {

    @Override
    public Long userId(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        return userId;
    }

    @Override
    public String userRole(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Role role = user.getAuthorities().iterator().next();
        return role.getAuthority();
    }
}
