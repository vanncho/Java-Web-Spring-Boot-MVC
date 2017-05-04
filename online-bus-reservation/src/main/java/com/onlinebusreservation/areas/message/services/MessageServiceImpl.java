package com.onlinebusreservation.areas.message.services;

import com.onlinebusreservation.areas.feedback.entities.Feedback;
import com.onlinebusreservation.areas.feedback.repositories.FeedbackRepository;
import com.onlinebusreservation.areas.message.entities.Message;
import com.onlinebusreservation.areas.message.models.binding.MessageSentToUserModel;
import com.onlinebusreservation.areas.message.models.view.MessageReadViewModel;
import com.onlinebusreservation.areas.message.models.view.MessageViewModel;
import com.onlinebusreservation.areas.message.repositories.MessageRepository;
import com.onlinebusreservation.areas.user.entities.User;
import com.onlinebusreservation.areas.user.repositories.AbstractUserRepository;
import com.onlinebusreservation.mappers.ModelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final AbstractUserRepository userRepository;

    private final FeedbackRepository feedbackRepository;

    private final ModelParser modelParser;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository,
                              AbstractUserRepository userRepository,
                              FeedbackRepository feedbackRepository,
                              ModelParser modelParser) {

        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
        this.modelParser = modelParser;
    }

    @Override
    @Transactional
    public void sendMessage(MessageSentToUserModel messageSentToUserModel) {

        User user = this.userRepository.findOne(messageSentToUserModel.getUserId());
        Message message = new Message();
        message.setIsRead(false);
        Date now = new Date();
        message.setDate(now);
        message.setMessage(messageSentToUserModel.getMessage());
        message.setUser(user);

        Feedback feedback = this.feedbackRepository.findOne(messageSentToUserModel.getFeedbackId());
        this.feedbackRepository.updateIsAnswered(messageSentToUserModel.getFeedbackId(), true);
        message.setFeedback(feedback);

        this.messageRepository.save(message);
    }

    @Override
    public List<MessageViewModel> getAllUserMessages() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();
        List<Message> userMessages = this.messageRepository.getUserMessages(userId);
        List<MessageViewModel> messageViewModels = new ArrayList<>();

        for (Message userMessage : userMessages) {

            MessageViewModel messageViewModel = this.modelParser.convert(userMessage, MessageViewModel.class);
            messageViewModels.add(messageViewModel);
        }

        return messageViewModels;
    }

    @Override
    public void deleteMessage(long messageId) {

        this.messageRepository.delete(messageId);
    }

    @Override
    @Transactional
    public void updateMessageStatus(long messageId) {

        Message message = this.messageRepository.findOne(messageId);
        message.setIsRead(true);
    }

    @Override
    public MessageReadViewModel getMessage(long messageId) {

        Message message = this.messageRepository.findOne(messageId);
        MessageReadViewModel messageReadViewModel = this.modelParser.convert(message, MessageReadViewModel.class);
        return messageReadViewModel;
    }
}
