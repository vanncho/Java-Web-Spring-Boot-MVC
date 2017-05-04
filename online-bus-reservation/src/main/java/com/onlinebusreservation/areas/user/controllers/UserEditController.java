package com.onlinebusreservation.areas.user.controllers;

import com.onlinebusreservation.areas.city.models.view.CityViewModel;
import com.onlinebusreservation.areas.city.services.CityService;
import com.onlinebusreservation.areas.user.exceptions.UserNotFoundException;
import com.onlinebusreservation.areas.user.model.binding.ChangeUserPasswordModel;
import com.onlinebusreservation.areas.user.model.binding.EditUserModel;
import com.onlinebusreservation.areas.user.model.view.RoleViewModel;
import com.onlinebusreservation.areas.user.model.view.UserNameViewModel;
import com.onlinebusreservation.areas.user.services.RoleService;
import com.onlinebusreservation.areas.user.services.BasicUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserEditController {

    private final BasicUserServiceImpl basicUserServiceImpl;

    private final CityService cityService;

    private final RoleService roleService;

    @Autowired
    public UserEditController(BasicUserServiceImpl basicUserServiceImpl,
                              CityService cityService,
                              RoleService roleService) {

        this.basicUserServiceImpl = basicUserServiceImpl;
        this.cityService = cityService;
        this.roleService = roleService;
    }

    @GetMapping("/edit/{id}")
    public String getEditUser(@PathVariable("id") Long id,
                              @ModelAttribute EditUserModel editUserModel,
                              Model model) {

        List<CityViewModel> cities = this.cityService.getAllCities();
        List<RoleViewModel> roles = this.roleService.getRoles();
        EditUserModel user = this.basicUserServiceImpl.findUser(id);

        model.addAttribute("title", "Edit User");
        model.addAttribute("editUserModel", user);
        model.addAttribute("cities", cities);
        model.addAttribute("roles", roles);
        model.addAttribute("view", "users/edit");

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editUser(Model model,
                          @PathVariable("id") Long id,
                          @Valid @ModelAttribute EditUserModel editUserModel,
                          BindingResult bindingResult) {

        List<CityViewModel> cities = this.cityService.getAllCities();
        List<RoleViewModel> roles = this.roleService.getRoles();

        if (bindingResult.hasErrors()) {

            model.addAttribute("user", editUserModel);
            model.addAttribute("cities", cities);
            model.addAttribute("roles", roles);
            model.addAttribute("view", "users/edit");
            return "base-layout";
        }

        this.basicUserServiceImpl.updateUser(editUserModel, id);
        return "redirect:/users";
    }

    @GetMapping("/change-password/{id}")
    public String getChangeUserPassword(@PathVariable("id") Long id,
                              @ModelAttribute ChangeUserPasswordModel changeUserPasswordModel,
                              Model model) {

        UserNameViewModel user = this.basicUserServiceImpl.getUserBy(id);

        model.addAttribute("title", "Change User Password");
        model.addAttribute("changeUserPasswordModel", user);
        model.addAttribute("view", "users/change-password");

        return "base-layout";
    }

    @PostMapping("/change-password/{id}")
    public String changeUserPassword(@PathVariable("id") Long id,
                                     @Valid @ModelAttribute ChangeUserPasswordModel changeUserPasswordModel,
                                     BindingResult bindingResult,
                                     Model model) {

        UserNameViewModel user = this.basicUserServiceImpl.getUserBy(id);
        changeUserPasswordModel.setUsername(user.getUsername());

        if (bindingResult.hasErrors()) {

            model.addAttribute("changeUserPasswordModel", changeUserPasswordModel);
            model.addAttribute("view", "users/change-password");
            return "base-layout";
        }

        this.basicUserServiceImpl.changePassword(changeUserPasswordModel, id);
        return "redirect:/users";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFound(){

        return "error/404";
    }
}
