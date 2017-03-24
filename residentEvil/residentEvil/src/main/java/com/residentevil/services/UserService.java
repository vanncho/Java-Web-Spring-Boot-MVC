package com.residentevil.services;

import com.residentevil.models.binding.user.UserRegisterModel;
import com.residentevil.models.view.user.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void create(UserRegisterModel userRegisterModel);

    List<UserViewModel> getAllUsers();

    void changeRole(String roleAndId);
}
