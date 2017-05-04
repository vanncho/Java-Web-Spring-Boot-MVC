package com.residentevil.controllers;

import com.residentevil.constants.Errors;
import com.residentevil.models.binding.user.UserRegisterModel;
import com.residentevil.models.view.role.RoleChangeView;
import com.residentevil.models.view.user.UserViewModel;
import com.residentevil.services.RoleService;
import com.residentevil.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/register")
    public String getRegister(Model model,
                              @ModelAttribute UserRegisterModel userRegisterModel) {

        model.addAttribute("title", "Register user");
        model.addAttribute("view", "user/register");

        return "base-layout";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserRegisterModel userRegisterModel,
                           BindingResult bindingResult,
                           Model model) {

        String password = userRegisterModel.getPassword();
        String confirmPassword = userRegisterModel.getConfirmPassword();
        boolean notMatchingPasswords = false;

        if (!password.equals(confirmPassword)) {

            model.addAttribute("confirmPassword", "Passwords are not matching.");
            notMatchingPasswords = true;
        }

        if (bindingResult.hasErrors() || notMatchingPasswords) {

            model.addAttribute("view", "user/register");
            return "base-layout";
        }

        this.userService.create(userRegisterModel);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(@RequestParam(required = false) String error,
                           Model model) {

        if (error != null) {

            model.addAttribute("error", Errors.WRONG_USERNAME_OR_PASSWORD);
        }

        model.addAttribute("title", "Login user");
        model.addAttribute("view", "user/login");
        return "base-layout";
    }

    @GetMapping("/logout")
    public String getLogout() {

        return "redirect:/";
    }

    @GetMapping("/unauthorized")
    public String getUnauthorizedPage(Model model) {

        model.addAttribute("title", "No Access");
        model.addAttribute("view", "errors/401");
        return "base-layout";
    }

    @GetMapping("/users")
    public String getUsersPage(Model model) {

        List<UserViewModel> users = this.userService.getAllUsers();
        List<RoleChangeView> roles = this.roleService.getAllRoles();

        model.addAttribute("title", "All Users");
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        model.addAttribute("view", "user/users-show");
        return "base-layout";
    }

    @PostMapping("/users")
    public String changeUserRole(Model model,
                                 @RequestParam("role") String role) {

        this.userService.changeRole(role);
        return "redirect:/users";
    }
}
