package com.onlinebusreservation.areas.user.controllers;

import com.onlinebusreservation.constants.Errors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserLoginController {

    @GetMapping("/login")
    public String getLogin(@RequestParam(required = false) String error,
                           Model model) {

        if (error != null) {

            model.addAttribute("error", Errors.WRONG_USERNAME_OR_PASSWORD);
        }

        model.addAttribute("title", "User Login");
        model.addAttribute("view", "users/login");
        return "base-layout";
    }
}
