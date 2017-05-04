package com.onlinebusreservation.areas.feedback.models.view;

import com.onlinebusreservation.areas.user.model.view.UserFeedbackViewModel;

public class FeedbackViewModel {

    private Long id;

    private String message;

    private String date;

    private UserFeedbackViewModel user;

    private boolean isAnswered;

    public FeedbackViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserFeedbackViewModel getUser() {
        return user;
    }

    public void setUser(UserFeedbackViewModel user) {
        this.user = user;
    }

    public boolean getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(boolean answered) {
        isAnswered = answered;
    }
}
