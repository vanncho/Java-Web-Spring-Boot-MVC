package com.residentevil.models.binding.user;

import javax.validation.constraints.Size;

public class UserRegisterModel {

    @Size(min = 5, message = "Username must be at least 5 symbols long.")
    private String username;

    @Size(min = 6, message = "Password must be at least 6 symbols long.")
    private String password;

    @Size(min = 6, message = "Password must be at least 6 symbols long.")
    private String confirmPassword;

    public UserRegisterModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
