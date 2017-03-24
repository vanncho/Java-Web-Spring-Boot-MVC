package com.cardealer.models.binding.user;

import com.cardealer.entities.enumerations.Role;

public class UserLoginModel {

    private String email;

    private String password;

    private Role role;

    public UserLoginModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
