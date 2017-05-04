package com.residentevil.services;

import com.residentevil.constants.Errors;
import com.residentevil.entities.Role;
import com.residentevil.entities.User;
import com.residentevil.mappers.ModelParser;
import com.residentevil.models.binding.user.UserRegisterModel;
import com.residentevil.models.view.user.UserViewModel;
import com.residentevil.repositories.RoleRepository;
import com.residentevil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelParser modelParser;

    @Transactional
    @Override
    public void create(UserRegisterModel userRegisterModel) {

        User user = this.modelParser.convert(userRegisterModel, User.class);

        String encryptedPassword = this.bCryptPasswordEncoder.encode(userRegisterModel.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        Role role = this.roleRepository.findOne(3L);
        user.getAuthorities().add(role);

        this.userRepository.save(user);
    }

    @Override
    public List<UserViewModel> getAllUsers() {

        List<User> users = this.userRepository.findAll();
        List<UserViewModel> userViewModels = new ArrayList<>();

        for (User user : users) {

            UserViewModel userViewModel = this.modelParser.convert(user, UserViewModel.class);

            String roleName = null;

            for (Role role : user.getAuthorities()) {

                roleName = role.getAuthority().substring(5);
            }

            userViewModel.setAuthority(roleName);
            userViewModels.add(userViewModel);
        }

        return userViewModels;
    }

    @Override
    public void changeRole(String roleAndId) {

        String[] tokens = roleAndId.split("=");
        Role role = this.roleRepository.findByAuthority("ROLE_" + tokens[0]);
        User user = this.userRepository.findOne(Long.valueOf(tokens[1]));

        user.getAuthorities().clear();
        user.getAuthorities().add(role);

        this.userRepository.save(user);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findOneByUsername(username);

        if (user == null) {

            throw new UsernameNotFoundException(Errors.WRONG_USERNAME_OR_PASSWORD);
        }

        return user;
    }
}
