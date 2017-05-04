package com.onlinebusreservation.areas.message.models.binding;

public class MessageSentToUserModel {

    private Long userId;

    private Long feedbackId;

    private String message;

    public MessageSentToUserModel() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
