package com.onlinebusreservation.areas.message.models.view;

import com.onlinebusreservation.areas.feedback.models.view.FeedbackReMessageViewModel;

public class MessageReadViewModel {

    private String message;

    private FeedbackReMessageViewModel feedbackMessage;

    public MessageReadViewModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FeedbackReMessageViewModel getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(FeedbackReMessageViewModel feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }
}
