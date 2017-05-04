package com.onlinebusreservation.areas.user.model.view;

public class UserFeedbackViewModel {

    private Long id;

    private String username;

    public UserFeedbackViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
