package com.onlinebusreservation.areas.user.services;

import com.onlinebusreservation.areas.user.model.binding.ChangeUserPasswordModel;
import com.onlinebusreservation.areas.user.model.binding.EditUserModel;
import com.onlinebusreservation.areas.user.model.binding.UserRegisterModel;
import com.onlinebusreservation.areas.user.model.view.UserDeleteModelView;
import com.onlinebusreservation.areas.user.model.view.UserNameViewModel;
import com.onlinebusreservation.areas.user.model.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface BasicUserService extends UserDetailsService {

    void create(UserRegisterModel userRegisterModel);

    List<UserViewModel> getAllUsers(String order);

    EditUserModel findUser(Long id);

    UserNameViewModel getUserBy(Long id);

    void changePassword(ChangeUserPasswordModel changeUserPasswordModel, Long id);

    UserDeleteModelView getDeleteUser(Long id);

    void deleteUser(Long id);

    void updateUser(EditUserModel editUserModel, Long id);

    boolean userNameIsFree(String userName);

    boolean emailExist(String email);
}



