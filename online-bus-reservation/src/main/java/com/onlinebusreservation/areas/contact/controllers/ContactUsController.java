package com.onlinebusreservation.areas.contact.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("contacts")
public class ContactUsController {

    @GetMapping("")
    public String getContactUsPage(Model model) {

        model.addAttribute("view", "contact/contact-form");
        return "base-layout";
    }
}
