package com.residentevil.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @GetMapping({"/","/home"})
    public String getHome(Model model) {

        model.addAttribute("title", "Home");
        model.addAttribute("view", "home");
        return "base-layout";
    }
}
