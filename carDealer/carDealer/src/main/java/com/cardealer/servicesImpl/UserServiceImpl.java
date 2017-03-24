package com.cardealer.servicesImpl;

import com.cardealer.entities.User;
import com.cardealer.entities.enumerations.Role;
import com.cardealer.mappers.ModelParser;
import com.cardealer.models.binding.user.UserLoginModel;
import com.cardealer.models.binding.user.UserRegisterModel;
import com.cardealer.repositories.UserRepository;
import com.cardealer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelParser modelParser;

    @Override
    public void create(UserRegisterModel userRegisterModel) {

        Role role = this.getUserGetRole();
        User user = this.modelParser.convert(userRegisterModel, User.class);
        user.setRole(role);

        this.userRepository.save(user);
    }

    @Override
    public boolean exists(String email) {

        User existUser = this.userRepository.exists(email);
        return existUser != null;
    }

    @Override
    public UserLoginModel getByUsernameAndPassword(String username, String password) {

        User user = this.userRepository.getByEmailAndPassword(username, password);
        UserLoginModel userLoginModel = null;

        if (user != null) {

            userLoginModel = this.modelParser.convert(user, UserLoginModel.class);
            Role role = user.getRole();
            userLoginModel.setRole(role);
        }

        return userLoginModel;
    }

    private Role getUserGetRole() {

        User hasAdmin = this.hasAdmin();
        Role role = Role.USER;

        if (hasAdmin == null) {

            role = Role.ADMIN;
            return role;
        }

        return role;
    }

    private User hasAdmin() {

        User adminUser = this.userRepository.hasAdmin();

        return adminUser;
    }
}
