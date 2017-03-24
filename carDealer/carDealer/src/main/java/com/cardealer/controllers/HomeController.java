package com.cardealer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("")
    public String getHome(Model model) {

        model.addAttribute("title", "Car Database");

        model.addAttribute("view", "home");
        return "base-layout";
    }
}
