package com.onlinebusreservation.areas.user.model.binding;

import com.onlinebusreservation.constants.Errors;

import javax.validation.constraints.Size;

public class ChangeUserPasswordModel implements UserPasswordMatcher {

    private String username;

    @Size(min = 6, message = Errors.USER_PASSWORD_LENGTH)
    private String password;

    @Size(min = 6, message = Errors.USER_PASSWORD_LENGTH)
    private String confirmPassword;

    public ChangeUserPasswordModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
