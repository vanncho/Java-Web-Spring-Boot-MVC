package com.onlinebusreservation.areas.user.model.binding;

import com.onlinebusreservation.constants.Errors;
import com.onlinebusreservation.constants.RegexPatterns;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditUserModel {

    private String username;

    @Pattern(regexp = RegexPatterns.REGEX_EMAIL, message = Errors.USER_EMAIL)
    private String email;

    @Size(min = 5, message = Errors.USER_FULL_NAME_LENGTH)
    @Pattern(regexp = RegexPatterns.REGEX_FULL_NAME, message = Errors.USER_FULL_NAME)
    private String fullName;

    private String cityName;

    private String address;

    @Pattern(regexp = RegexPatterns.REGEX_MOBILE_NUMBER, message = Errors.USER_MOBILE_NUMBER)
    private String mobile;

    private String accountNonLocked;

    private String enabled;

    private String authority;

    public EditUserModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(String accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
