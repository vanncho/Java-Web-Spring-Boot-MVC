package com.cardealer.controllers;


import com.cardealer.constants.Messages;
import com.cardealer.models.binding.user.UserLoginModel;
import com.cardealer.models.binding.user.UserRegisterModel;
import com.cardealer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/logout")
    public String getLogout(Model model, HttpSession session) {

        session.invalidate();

        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {

        model.addAttribute("title", "Login");
        model.addAttribute("view", "user/login");

        return "base-layout";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute UserLoginModel userLoginModel,
                          HttpSession session,
                          Model model) {

        model.addAttribute("title", "Login");
        UserLoginModel userToLogIn = this.userService.getByUsernameAndPassword(userLoginModel.getEmail(), userLoginModel.getPassword());
        List<String> errors = new ArrayList<>();

        if (userToLogIn != null) {

            model.addAttribute("user", userToLogIn);
            session.setAttribute("loggedUser", userToLogIn);
            return "redirect:/";

        } else {

            errors.add(Messages.NON_EXISTING_USER);
            model.addAttribute("errors", errors);
            model.addAttribute("view", "user/login");
        }

        return "base-layout";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {

        model.addAttribute("title", "Register");
        model.addAttribute("view", "user/register");

        return "base-layout";
    }

    @PostMapping("/register")
    public String doUserRegister(@ModelAttribute UserRegisterModel userRegisterModel,
                                 Model model) {

        List<String> errors = new ArrayList<>();
        boolean exitUser = this.userService.exists(userRegisterModel.getEmail());

        if (!exitUser) {

            this.userEmailValidation(errors, userRegisterModel.getEmail());
            this.passwordValidation(errors, userRegisterModel.getPassword(), userRegisterModel.getConfirmPassword());

            if (errors.size() > 0) {

                model.addAttribute("errors", errors);
                model.addAttribute("view", "user/register");
            } else {

                this.userService.create(userRegisterModel);
                return "redirect:/user/login";
            }

        } else {

            errors.add(Messages.EXISTING_USER);
            model.addAttribute("errors", errors);
            model.addAttribute("view", "user/register");
        }

        return "base-layout";
    }

    private boolean userEmailValidation(List<String> errors, String email) {

        boolean isValid;

        if (!email.contains("@") && !email.contains(".")) {

            isValid = false;
            errors.add(Messages.WRONG_EMAIL);
        } else {

            isValid = true;
        }

        return isValid;
    }

    private boolean passwordValidation(List<String> errors, String password, String repeatPassword) {

        boolean isValid = true;
        String passPattern = "^[a-zA-Z0-9]{6,}$";
        Pattern pattern = Pattern.compile(passPattern);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.find()) {

            isValid = false;
            errors.add(Messages.WRONG_PASSWORD);
        }

        if (!password.equals(repeatPassword)) {

            isValid = false;
            errors.add(Messages.MISMATCH_PASSWORDS);
        }

        return isValid;
    }
}
