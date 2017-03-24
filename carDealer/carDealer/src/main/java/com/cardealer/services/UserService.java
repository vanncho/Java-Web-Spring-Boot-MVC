package com.cardealer.services;

import com.cardealer.models.binding.user.UserLoginModel;
import com.cardealer.models.binding.user.UserRegisterModel;

public interface UserService {

    void create(UserRegisterModel userRegisterModel);

    boolean exists(String username);

    UserLoginModel getByUsernameAndPassword(String username, String password);
}
