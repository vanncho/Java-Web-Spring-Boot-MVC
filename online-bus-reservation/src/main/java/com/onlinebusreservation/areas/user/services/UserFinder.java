package com.onlinebusreservation.areas.user.services;

import com.onlinebusreservation.areas.user.entities.Role;
import org.springframework.security.core.Authentication;

public interface UserFinder {

    Long userId(Authentication authentication);

    String userRole(Authentication authentication);
}
