package com.onlinebusreservation.areas.feedback.controllers;

import com.onlinebusreservation.areas.feedback.models.view.FeedbackViewModel;
import com.onlinebusreservation.areas.feedback.services.FeedbackService;
import com.onlinebusreservation.areas.message.models.binding.MessageSentToUserModel;
import com.onlinebusreservation.areas.message.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback/feedbacks")
public class FeedbackRestController {

    private final FeedbackService feedbackService;

    private final MessageService messageService;

    @Autowired
    public FeedbackRestController(FeedbackService feedbackService, MessageService messageService) {

        this.feedbackService = feedbackService;
        this.messageService = messageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeedbackViewModel>> getAllFeedbacks() {

        List<FeedbackViewModel> feedbacks = this.feedbackService.getFeedbacks();
        ResponseEntity<List<FeedbackViewModel>> responseEntity = new ResponseEntity(feedbacks, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/delete/{feedbackId}")
    public ResponseEntity deleteFeedback(@PathVariable long feedbackId) {

        this.feedbackService.deleteFeedback(feedbackId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/reply/{feedbackId}")
    public ResponseEntity<FeedbackViewModel> getReplyFeedbacks(@PathVariable long feedbackId) {

        FeedbackViewModel feedback = this.feedbackService.getFeedbackById(feedbackId);
        ResponseEntity<FeedbackViewModel> responseEntity = new ResponseEntity(feedback, HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/send")
    public ResponseEntity sendMessage(@RequestBody MessageSentToUserModel messageSentToUserModel) {

        this.messageService.sendMessage(messageSentToUserModel);
        return new ResponseEntity(HttpStatus.OK);
    }
}
