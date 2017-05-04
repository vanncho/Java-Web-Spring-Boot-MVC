package com.onlinebusreservation.areas.user.controllers;

import com.onlinebusreservation.areas.city.models.view.CityViewModel;
import com.onlinebusreservation.areas.city.services.CityService;
import com.onlinebusreservation.areas.user.model.binding.UserRegisterModel;
import com.onlinebusreservation.areas.user.services.BasicUserService;
import com.onlinebusreservation.constants.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserRegisterController {


    private final BasicUserService basicUserService;


    private final CityService cityService;

    @Autowired
    public UserRegisterController(BasicUserService basicUserService,
                                  CityService cityService) {

        this.basicUserService = basicUserService;
        this.cityService = cityService;
    }

    @GetMapping("/register")
    public String getRegisterUser(Model model,
                                  @ModelAttribute UserRegisterModel userRegisterModel) {

        List<CityViewModel> cities = this.cityService.getAllCities();
        model.addAttribute("title", "User registration");

        model.addAttribute("cities", cities);
        model.addAttribute("view", "users/register");

        return "base-layout";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserRegisterModel userRegisterModel,
                               BindingResult bindingResult,
                               Model model) {

        List<CityViewModel> cities = this.cityService.getAllCities();

        String username = userRegisterModel.getUsername();
        boolean freeUsername = this.basicUserService.userNameIsFree(username);

        String email = userRegisterModel.getEmail();
        boolean existingEmail = this.basicUserService.emailExist(email);

        if (!freeUsername) {

            FieldError fieldError = new FieldError("usernameExist", "username", Errors.USER_EXIST);
            bindingResult.addError(fieldError);
        }

        if (existingEmail) {

            FieldError fieldError = new FieldError("emailExist", "email", Errors.EXISTING_EMAIL);
            bindingResult.addError(fieldError);
        }

        if (bindingResult.hasErrors()) {

            model.addAttribute("cities", cities);
            model.addAttribute("view", "users/register");
            return "base-layout";
        }

        this.basicUserService.create(userRegisterModel);
        return "redirect:/user/login";
    }
}
