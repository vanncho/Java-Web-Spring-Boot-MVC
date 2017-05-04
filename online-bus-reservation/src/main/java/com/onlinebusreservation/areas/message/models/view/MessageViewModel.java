package com.onlinebusreservation.areas.message.models.view;

import com.onlinebusreservation.areas.feedback.models.view.FeedbackReMessageViewModel;

public class MessageViewModel {

    private Long id;

    private String date;

    private String message;

    private boolean isRead;

    private FeedbackReMessageViewModel feedbackMessage;

    public MessageViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean read) {
        isRead = read;
    }

    public FeedbackReMessageViewModel getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(FeedbackReMessageViewModel feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }
}
