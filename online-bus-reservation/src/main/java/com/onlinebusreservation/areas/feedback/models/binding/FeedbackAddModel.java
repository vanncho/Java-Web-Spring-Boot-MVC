package com.onlinebusreservation.areas.feedback.models.binding;

import com.onlinebusreservation.constants.Errors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FeedbackAddModel {

    @NotNull(message = Errors.NO_FEEDBACK)
    @Size(min = 20, message = Errors.FEEDBACK_LENGTH)
    private String message;

    public FeedbackAddModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
