package com.onlinebusreservation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unauthorized")
public class AccessController {

    @GetMapping("")
    public String getUnauthorized() {

        return "error/401";
    }
}
