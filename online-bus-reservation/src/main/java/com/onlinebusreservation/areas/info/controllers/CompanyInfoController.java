package com.onlinebusreservation.areas.info.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info")
public class CompanyInfoController {

    @GetMapping("/info")
    public String getCompanyInfo() {

        return "company-info/info";
    }
}
