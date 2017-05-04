package com.onlinebusreservation.areas.message.controllers;

import com.google.gson.Gson;
import com.onlinebusreservation.areas.message.models.view.MessageReadViewModel;
import com.onlinebusreservation.areas.message.models.view.MessageViewModel;
import com.onlinebusreservation.areas.message.services.MessageService;
import com.onlinebusreservation.interceptors.UserUnreadMessagesInterceptor;
import com.onlinebusreservation.interceptors.ValidBookingCheckInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageRestController.class)
@ActiveProfiles("test")
public class MessageRestControllerTest {

    private static final String GET_URL = "/messages/messages/all";
    private static final String DELETE_URL = "/messages/messages/delete/{id}";
    private static final String PUT_URL = "/messages/messages/update/{id}";
    private static final String GET_READ_URL = "/messages/messages/{id}";

    private static final Long MSG_ONE_ID = 1L;
    private static final Long MSG_TWO_ID = 2L;
    private static final String MSG_ONE_DATE = "2017-05-03";
    private static final String MSG_TWO_DATE = "2017-04-03";
    private static final String MSG_ONE_MESSAGE = "Some test message one.";
    private static final String MSG_TWO_MESSAGE = "Some test message two.";

    private static final Integer COLLECTION_REST_SIZE = 2;
    private static final Integer METHOD_CALL_TIMES = 1;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessageService messageService;

    @MockBean
    private ValidBookingCheckInterceptor bookingCheckInterceptor;

    @MockBean
    private UserUnreadMessagesInterceptor userUnreadMessagesInterceptor;

    @Captor
    private ArgumentCaptor<HttpServletRequest> servletRequestCaptor;

    @Captor
    private ArgumentCaptor<HttpServletResponse> servletResponseCaptor;

    @Captor
    private ArgumentCaptor<Object> objectCaptor;

    @Before
    public void setUp() throws Exception {

        when(this.bookingCheckInterceptor.preHandle(this.servletRequestCaptor.capture(), this.servletResponseCaptor.capture(), this.objectCaptor.capture())).thenReturn(true);
        when(this.userUnreadMessagesInterceptor.preHandle(this.servletRequestCaptor.capture(), this.servletResponseCaptor.capture(), this.objectCaptor.capture())).thenReturn(true);

        MessageViewModel messageOne = new MessageViewModel();
        messageOne.setId(MSG_ONE_ID);
        messageOne.setDate(MSG_ONE_DATE);
        messageOne.setMessage(MSG_ONE_MESSAGE);

        MessageViewModel messageTwo = new MessageViewModel();
        messageTwo.setId(MSG_TWO_ID);
        messageTwo.setDate(MSG_TWO_DATE);
        messageTwo.setMessage(MSG_TWO_MESSAGE);

        List<MessageViewModel> messages = Arrays.asList(messageOne, messageTwo);

        when(this.messageService.getAllUserMessages()).thenReturn(messages);

        MessageReadViewModel messageReadViewModel = new MessageReadViewModel();
        messageReadViewModel.setMessage(MSG_TWO_MESSAGE);

        when(this.messageService.getMessage(MSG_TWO_ID)).thenReturn(messageReadViewModel);
    }

    @Test
    public void getAllUserMessages_ShouldReturnMessagesCollectionModel() throws Exception {

        this.mvc
                .perform(get(GET_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(COLLECTION_REST_SIZE)));

        verify(this.messageService, times(1)).getAllUserMessages();
        verifyNoMoreInteractions(this.messageService);
    }

    @Test
    public void deleteMessage_ShouldSuccessfullyDeleteMessage() throws Exception {

        this.mvc
                .perform(delete(DELETE_URL,  MSG_ONE_ID))
                .andExpect(status().isOk());

        verify(this.messageService, times(METHOD_CALL_TIMES)).deleteMessage(MSG_ONE_ID);
        verifyNoMoreInteractions(this.messageService);
    }

    @Test
    public void updateMessage_ShouldSuccessfullyUpdateMessage() throws Exception {

        MessageReadViewModel messageReadViewModel = new MessageReadViewModel();
        messageReadViewModel.setMessage(MSG_TWO_MESSAGE);
        String json = new Gson().toJson(messageReadViewModel);

        this.mvc
                .perform(put(PUT_URL, MSG_ONE_ID)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andExpect(status().isOk());

        verify(this.messageService, times(METHOD_CALL_TIMES)).updateMessageStatus(MSG_ONE_ID);
        verifyNoMoreInteractions(this.messageService);
    }

    @Test
    public void getFullMessages_ShouldReturnReadMessageModelSuccessfully() throws Exception {

        this.mvc
                .perform(get(GET_READ_URL, MSG_TWO_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is(MSG_TWO_MESSAGE)));

        verify(this.messageService, times(METHOD_CALL_TIMES)).getMessage(MSG_TWO_ID);
        verifyNoMoreInteractions(this.messageService);
    }

}