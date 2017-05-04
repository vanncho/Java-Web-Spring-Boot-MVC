package com.onlinebusreservation.areas.user.model.binding;

import com.onlinebusreservation.constants.Errors;
import com.onlinebusreservation.constants.RegexPatterns;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterModel implements UserPasswordMatcher {

    @Size(min = 5, message = Errors.USERNAME_LENGTH)
    private String username;

    @Size(min = 6, message = Errors.USER_PASSWORD_LENGTH)
    private String password;

    @Size(min = 6, message = Errors.USER_PASSWORD_LENGTH)
    private String confirmPassword;

    @Pattern(regexp = RegexPatterns.REGEX_EMAIL, message = Errors.USER_EMAIL)
    private String email;

    @Size(min = 5, message = Errors.USER_FULL_NAME_LENGTH)
    @Pattern(regexp = RegexPatterns.REGEX_FULL_NAME, message = Errors.USER_FULL_NAME)
    private String fullName;

    private String cityName;

    private String address;

    @Pattern(regexp = RegexPatterns.REGEX_MOBILE_NUMBER, message = Errors.USER_MOBILE_NUMBER)
    private String mobile;

    public UserRegisterModel() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
