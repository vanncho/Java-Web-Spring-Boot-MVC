package com.onlinebusreservation.areas.feedback.services;

import com.onlinebusreservation.areas.feedback.models.binding.FeedbackAddModel;
import com.onlinebusreservation.areas.feedback.models.view.FeedbackViewModel;

import java.util.List;

public interface FeedbackService {

    void addFeedback(FeedbackAddModel feedbackAddModel);

    List<FeedbackViewModel> getFeedbacks();

    void deleteFeedback(long feedbackId);

    FeedbackViewModel getFeedbackById(long feedbackId);
}
