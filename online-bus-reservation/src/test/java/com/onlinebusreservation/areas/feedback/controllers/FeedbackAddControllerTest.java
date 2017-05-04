package com.onlinebusreservation.areas.feedback.controllers;

import com.onlinebusreservation.areas.feedback.models.binding.FeedbackAddModel;
import com.onlinebusreservation.areas.feedback.services.FeedbackService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FeedbackAddController.class)
@ActiveProfiles("test")
public class FeedbackAddControllerTest {

    private static final String GET_URL = "/feedback/add";
    private static final String VIEW_NAME = "base-layout";

    private static final String MESSAGE = "What is Lorem Ipsum el dome.";
    private static final String REDIRECT_VIEW_NAME = "redirect:/";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FeedbackService feedbackService;

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

        FeedbackAddModel feedbackAddModel = new FeedbackAddModel();
        feedbackAddModel.setMessage(MESSAGE);
    }

    @Test
    public void getAddFeedback_ShouldShowCorrectViewModel() throws Exception {

        this.mvc
                .perform(get(GET_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME));
    }

    @Test
    public void addFeedbackWithMessage_ShouldSendFeedback() throws Exception {

        this.mvc
                .perform(post(GET_URL)
                .param("message", MESSAGE))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW_NAME))
                .andExpect(redirectedUrl("/"));

    }

    @Test
    public void addFeedbackWithNoMessage_ShouldNotSendFeedbackAndReturnViewError() throws Exception {

        this.mvc
                .perform(post(GET_URL))
                .andExpect(model().hasErrors())
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME));

    }

}