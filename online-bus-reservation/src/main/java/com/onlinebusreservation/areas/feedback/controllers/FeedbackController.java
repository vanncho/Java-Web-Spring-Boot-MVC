package com.onlinebusreservation.areas.feedback.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @GetMapping("/feedbacks")
    public String getSinglePage(){

        return "feedback/feedbacks";
    }
}
