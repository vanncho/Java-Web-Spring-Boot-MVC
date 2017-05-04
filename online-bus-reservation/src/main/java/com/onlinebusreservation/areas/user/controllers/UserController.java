package com.onlinebusreservation.areas.user.controllers;

import com.onlinebusreservation.areas.user.model.view.UserViewModel;
import com.onlinebusreservation.areas.user.services.BasicUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private final BasicUserServiceImpl basicUserServiceImpl;

    @Autowired
    public UserController(BasicUserServiceImpl basicUserServiceImpl) {

        this.basicUserServiceImpl = basicUserServiceImpl;
    }

    @GetMapping("/logout")
    public String getLogout() {

        return "redirect:/user/login";
    }

    @GetMapping("/users")
    public String getUsers(Model model,
                           @RequestParam(required = false) String order) {

        List<UserViewModel> users = this.basicUserServiceImpl.getAllUsers(order);

        model.addAttribute("title", "Users Management");
        model.addAttribute("users", users);
        model.addAttribute("view", "/users/users");

        return "base-layout";
    }
}
