package com.onlinebusreservation.areas.feedback.controllers;

import com.onlinebusreservation.areas.feedback.models.binding.FeedbackAddModel;
import com.onlinebusreservation.areas.feedback.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/feedback")
public class FeedbackAddController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackAddController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/add")
    public String getAddFeedback(Model model,
                                 @ModelAttribute FeedbackAddModel feedbackAddMode) {

        model.addAttribute("view", "/feedback/add");
        return "base-layout";
    }

    @PostMapping("/add")
    public String addFeedback(Model model,
                              @Valid @ModelAttribute FeedbackAddModel feedbackAddModel,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("view", "/feedback/add");
            return "base-layout";
        }

        this.feedbackService.addFeedback(feedbackAddModel);
        return "redirect:/";
    }
}
