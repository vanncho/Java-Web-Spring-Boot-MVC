package com.onlinebusreservation.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public String getException(Model model) {

        model.addAttribute("view", "error/exception");
        return "base-layout";
    }
}