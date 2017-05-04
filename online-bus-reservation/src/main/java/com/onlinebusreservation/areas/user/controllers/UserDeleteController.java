package com.onlinebusreservation.areas.user.controllers;

import com.onlinebusreservation.areas.city.models.view.CityViewModel;
import com.onlinebusreservation.areas.city.services.CityService;
import com.onlinebusreservation.areas.user.exceptions.UserNotFoundException;
import com.onlinebusreservation.areas.user.model.view.RoleViewModel;
import com.onlinebusreservation.areas.user.model.view.UserDeleteModelView;
import com.onlinebusreservation.areas.user.services.RoleService;
import com.onlinebusreservation.areas.user.services.BasicUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserDeleteController {

    private final BasicUserServiceImpl basicUserServiceImpl;

    private final CityService cityService;

    private final RoleService roleService;

    @Autowired
    private UserDeleteController(BasicUserServiceImpl basicUserServiceImpl,
                                 CityService cityService,
                                 RoleService roleService) {

        this.basicUserServiceImpl = basicUserServiceImpl;
        this.cityService = cityService;
        this.roleService = roleService;
    }

    @GetMapping("/delete/{id}")
    public String getDeleteUser(@PathVariable("id") Long id,
                                Model model) {

        List<CityViewModel> cities = this.cityService.getAllCities();
        List<RoleViewModel> roles = this.roleService.getRoles();
        UserDeleteModelView user = this.basicUserServiceImpl.getDeleteUser(id);

        model.addAttribute("title", "Delete User");
        model.addAttribute("deleteUserModel", user);
        model.addAttribute("cities", cities);
        model.addAttribute("roles", roles);
        model.addAttribute("view", "users/delete");

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {

        this.basicUserServiceImpl.deleteUser(id);
        return "redirect:/users";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFound(){

        return "error/404";
    }
}
