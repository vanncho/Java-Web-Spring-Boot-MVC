package com.onlinebusreservation.areas.message.services;

import com.onlinebusreservation.areas.message.models.binding.MessageSentToUserModel;
import com.onlinebusreservation.areas.message.models.view.MessageReadViewModel;
import com.onlinebusreservation.areas.message.models.view.MessageViewModel;

import java.util.List;

public interface MessageService {

    void sendMessage(MessageSentToUserModel messageSentToUserModel);

    List<MessageViewModel> getAllUserMessages();

    void deleteMessage(long messageId);

    void updateMessageStatus(long messageId);

    MessageReadViewModel getMessage(long messageId);
}
