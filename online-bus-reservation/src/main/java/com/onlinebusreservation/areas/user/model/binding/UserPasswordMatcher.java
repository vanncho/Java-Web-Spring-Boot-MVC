package com.onlinebusreservation.areas.user.model.binding;

import com.onlinebusreservation.areas.user.annotations.PasswordsMatching;

@PasswordsMatching
public interface UserPasswordMatcher {

    String getPassword();

    String getConfirmPassword();
}
