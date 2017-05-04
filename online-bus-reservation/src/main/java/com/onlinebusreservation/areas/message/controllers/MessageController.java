package com.onlinebusreservation.areas.message.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @GetMapping("/messages")
    public String getAllMessages() {

        return "messages/messages";
    }
}
