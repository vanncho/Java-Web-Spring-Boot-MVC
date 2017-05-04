package com.onlinebusreservation.areas.message.controllers;

import com.onlinebusreservation.areas.message.models.view.MessageReadViewModel;
import com.onlinebusreservation.areas.message.models.view.MessageViewModel;
import com.onlinebusreservation.areas.message.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages/messages")
public class MessageRestController {

    private final MessageService messageService;

    @Autowired
    public MessageRestController(MessageService messageService) {

        this.messageService = messageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MessageViewModel>> getAllUserMessages() {

        List<MessageViewModel> userMessages = this.messageService.getAllUserMessages();
        ResponseEntity<List<MessageViewModel>> responseEntity = new ResponseEntity<>(userMessages, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity deleteMessage(@PathVariable long messageId) {

        this.messageService.deleteMessage(messageId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update/{messageId}")
    public ResponseEntity updateMessage(@PathVariable long messageId) {

        this.messageService.updateMessageStatus(messageId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageReadViewModel> getFullMessages(@PathVariable long messageId) {

        MessageReadViewModel fullMessage = this.messageService.getMessage(messageId);
        ResponseEntity<MessageReadViewModel> responseEntity = new ResponseEntity<>(fullMessage, HttpStatus.OK);
        return responseEntity;
    }
}
