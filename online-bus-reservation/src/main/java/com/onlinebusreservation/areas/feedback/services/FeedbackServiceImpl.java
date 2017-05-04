package com.onlinebusreservation.areas.feedback.services;

import com.onlinebusreservation.areas.feedback.entities.Feedback;
import com.onlinebusreservation.areas.feedback.models.binding.FeedbackAddModel;
import com.onlinebusreservation.areas.feedback.models.view.FeedbackViewModel;
import com.onlinebusreservation.areas.feedback.repositories.FeedbackRepository;
import com.onlinebusreservation.areas.user.entities.User;
import com.onlinebusreservation.mappers.ModelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final ModelParser modelParser;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, ModelParser modelParser) {
        this.feedbackRepository = feedbackRepository;
        this.modelParser = modelParser;
    }

    @Override
    public void addFeedback(FeedbackAddModel feedbackAddModel) {

        Feedback feedback = this.modelParser.convert(feedbackAddModel, Feedback.class);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        feedback.setUser(user);
        Date today = new Date();
        feedback.setDate(today);
        feedback.setIsAnswered(false);
        feedback.setMessage(feedback.getMessage());
        this.feedbackRepository.save(feedback);
    }

    @Override
    public List<FeedbackViewModel> getFeedbacks() {

        List<Feedback> feedbacks = this.feedbackRepository.findAll();
        List<FeedbackViewModel> feedbackViewModels = new ArrayList<>();

        for (Feedback feedback : feedbacks) {

            FeedbackViewModel feedbackViewModel = this.modelParser.convert(feedback, FeedbackViewModel.class);
            feedbackViewModels.add(feedbackViewModel);
        }

        return feedbackViewModels;
    }

    @Override
    public void deleteFeedback(long feedbackId) {

        this.feedbackRepository.delete(feedbackId);
    }

    @Override
    public FeedbackViewModel getFeedbackById(long feedbackId) {

        Feedback feedback = this.feedbackRepository.findOne(feedbackId);
        FeedbackViewModel feedbackViewModel = this.modelParser.convert(feedback, FeedbackViewModel.class);
        return feedbackViewModel;
    }
}
