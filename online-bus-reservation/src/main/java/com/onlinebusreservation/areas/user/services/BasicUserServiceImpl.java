package com.onlinebusreservation.areas.user.services;

import com.onlinebusreservation.areas.user.entities.BasicUser;
import com.onlinebusreservation.areas.user.entities.Role;
import com.onlinebusreservation.areas.user.entities.User;
import com.onlinebusreservation.areas.user.exceptions.UserNotFoundException;
import com.onlinebusreservation.areas.user.model.binding.ChangeUserPasswordModel;
import com.onlinebusreservation.areas.user.model.binding.EditUserModel;
import com.onlinebusreservation.areas.user.model.view.UserDeleteModelView;
import com.onlinebusreservation.areas.user.model.view.UserNameViewModel;
import com.onlinebusreservation.areas.user.model.view.UserViewModel;
import com.onlinebusreservation.areas.user.repositories.BasicUserRepository;
import com.onlinebusreservation.areas.user.repositories.RoleRepository;
import com.onlinebusreservation.constants.Errors;
import com.onlinebusreservation.areas.city.entities.City;
import com.onlinebusreservation.mappers.ModelParser;
import com.onlinebusreservation.areas.user.model.binding.UserRegisterModel;
import com.onlinebusreservation.areas.city.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BasicUserServiceImpl implements BasicUserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private BasicUserRepository basicUserRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelParser modelParser;

    @Override
    public void create(UserRegisterModel userRegisterModel) {

        BasicUser user = this.modelParser.convert(userRegisterModel, BasicUser.class);

        String encryptedPassword = this.bCryptPasswordEncoder.encode(userRegisterModel.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        City city = this.cityRepository.findOneByName(userRegisterModel.getCityName());
        user.setCity(city);

        Role role = this.roleRepository.findOne(2L);
        user.getAuthorities().add(role);

        this.basicUserRepository.save(user);
    }

    @Override
    public List<UserViewModel> getAllUsers(String role) {

        List<BasicUser> users = null;

        if (role != null) {

            switch (role) {

                case "USER":
                    users = this.basicUserRepository.getAllByRoleAdmin("ROLE_" + role);
                    break;
                case "ADMIN":
                    users = this.basicUserRepository.getAllByRoleUser("ROLE_" + role);
                    break;
                case "ALL":
                    users = this.basicUserRepository.findAll();
            }
        } else {

            users = this.basicUserRepository.findAll();
        }

        List<UserViewModel> userViewModels = new ArrayList<>();

        for (BasicUser user : users) {

            UserViewModel userViewModel = this.modelParser.convert(user, UserViewModel.class);
            List<Role> roles = new ArrayList<>(user.getAuthorities());
            userViewModel.setRoleName(roles.get(0).getAuthority().substring(5));
            userViewModels.add(userViewModel);
        }

        return userViewModels;
    }

    @Override
    public EditUserModel findUser(Long id) {

        BasicUser user = this.basicUserRepository.findOne(id);

        if (user == null) {

            throw new UserNotFoundException();
        }

        EditUserModel editUserModel = this.modelParser.convert(user, EditUserModel.class);
        List<Role> roles = new ArrayList<>(user.getAuthorities());
        editUserModel.setAuthority(roles.get(0).getAuthority().substring(5));
        return editUserModel;
    }

    @Override
    public UserNameViewModel getUserBy(Long id) {

        BasicUser user = this.basicUserRepository.findOne(id);

        if (user == null) {

            throw new UserNotFoundException();
        }

        UserNameViewModel userNameViewModel = this.modelParser.convert(user, UserNameViewModel.class);
        return userNameViewModel;
    }

    @Override
    public void changePassword(ChangeUserPasswordModel changeUserPasswordModel, Long id) {

        String newPassword = this.bCryptPasswordEncoder.encode(changeUserPasswordModel.getPassword());
        this.basicUserRepository.changePassword(id, newPassword);
    }

    @Override
    public UserDeleteModelView getDeleteUser(Long id) {

        BasicUser user = this.basicUserRepository.findOne(id);

        if (user == null) {

            throw new UserNotFoundException();
        }

        UserDeleteModelView userDeleteModelView = this.modelParser.convert(user, UserDeleteModelView.class);
        return userDeleteModelView;
    }

    @Override
    public void deleteUser(Long id) {

        User user = this.basicUserRepository.findOne(id);

        if (user == null) {

            throw new UserNotFoundException();
        }

        this.basicUserRepository.delete(id);
    }

    @Override
    public void updateUser(EditUserModel editUserModel, Long id) {

        String email = editUserModel.getEmail();
        String fullName = editUserModel.getFullName();
        City city = this.cityRepository.findOneByName(editUserModel.getCityName());
        String address = editUserModel.getAddress();
        String mobile = editUserModel.getMobile();
        boolean accountNonLocked = Boolean.valueOf(editUserModel.getAccountNonLocked());
        boolean enabled = Boolean.valueOf(editUserModel.getEnabled());
        Role role = this.roleRepository.findByAuthority("ROLE_" + editUserModel.getAuthority());

        BasicUser user = this.basicUserRepository.findOne(id);

        if (user == null) {

            throw new UserNotFoundException();
        }

        user.setEmail(email);
        user.setFullName(fullName);
        user.setCity(city);
        user.setAddress(address);
        user.setMobile(mobile);
        user.setAccountNonLocked(accountNonLocked);
        user.setEnabled(enabled);
        user.getAuthorities().clear();
        user.getAuthorities().add(role);

        this.basicUserRepository.save(user);
    }

    @Override
    public boolean userNameIsFree(String username) {

        User user = this.basicUserRepository.findOneByUsername(username);
        return user == null;
    }

    @Override
    public boolean emailExist(String email) {

        User user = this.basicUserRepository.findOneByEmail(email);
        return user != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.basicUserRepository.findOneByUsername(username);

        if (user == null) {

            throw new UsernameNotFoundException(Errors.WRONG_USERNAME_OR_PASSWORD);
        }

        return user;
    }
}
