package com.onlinebusreservation.areas.feedback.controllers;

import com.google.gson.Gson;
import com.onlinebusreservation.areas.feedback.models.view.FeedbackViewModel;
import com.onlinebusreservation.areas.feedback.services.FeedbackService;
import com.onlinebusreservation.areas.message.models.binding.MessageSentToUserModel;
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
import org.springframework.test.web.servlet.ResultMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FeedbackRestController.class)
@ActiveProfiles("test")
public class FeedbackRestControllerTest {

    private static final String GET_URL = "/feedback/feedbacks/all";

    private static final Integer COLLECTION_REST_SIZE = 2;
    private static final Integer METHOD_CALL_TIMES = 1;

    private static final long FEEDBACK_ID = 1;
    private static final String DELETE_URL = "/feedback/feedbacks/delete";
    private static final String REPLY_URL = "/feedback/feedbacks/reply";
    private static final String POST_URL = "/feedback/feedbacks/send";

    private static final String MESSAGE = "Some message to test with rest.";
    private static final long USER_ID = 10;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FeedbackService feedbackService;

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

        FeedbackViewModel feedbackOne = new FeedbackViewModel();
        feedbackOne.setId(FEEDBACK_ID);
        feedbackOne.setIsAnswered(false);

        FeedbackViewModel feedbackTwo = new FeedbackViewModel();
        List<FeedbackViewModel> feedbacks = Arrays.asList(feedbackOne, feedbackTwo);

        when(this.feedbackService.getFeedbacks()).thenReturn(feedbacks);
        when(this.feedbackService.getFeedbackById(FEEDBACK_ID)).thenReturn(feedbackOne);
    }

    @Test
    public void getAllFeedbacks_ShouldReturnFeedbacksCollectionModel() throws Exception {

        this.mvc
                .perform(get(GET_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(COLLECTION_REST_SIZE)));

        verify(this.feedbackService, times(METHOD_CALL_TIMES)).getFeedbacks();
        verifyNoMoreInteractions(this.feedbackService);
    }

    @Test
    public void deleteFeedback_ShouldSuccessfullyDeleteFeedback() throws Exception {

        this.mvc
                .perform(delete(DELETE_URL + "/" + FEEDBACK_ID))
                .andExpect(status().isOk());

        verify(this.feedbackService, times(METHOD_CALL_TIMES)).deleteFeedback(FEEDBACK_ID);
        verifyNoMoreInteractions(this.feedbackService);
    }

    @Test
    public void getReplyFeedbacks_ShouldReturnFeedbackByIdToReply() throws Exception {

        this.mvc
                .perform(get(REPLY_URL + "/" + FEEDBACK_ID))
                .andExpect(jsonPath("$.id", is((int)FEEDBACK_ID)))
                .andExpect((ResultMatcher) jsonPath("$.isAnswered", is(false)));

        verify(this.feedbackService, times(METHOD_CALL_TIMES)).getFeedbackById(FEEDBACK_ID);
        verifyNoMoreInteractions(this.feedbackService);
    }

    @Test
    public void sendMessage_ShouldSendMessageToUser() throws Exception {

        MessageSentToUserModel messageSentToUserModel = new MessageSentToUserModel();
        messageSentToUserModel.setFeedbackId(FEEDBACK_ID);
        messageSentToUserModel.setMessage(MESSAGE);
        messageSentToUserModel.setUserId(USER_ID);
        String json = new Gson().toJson(messageSentToUserModel);

        this.mvc
                .perform(post(POST_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

}