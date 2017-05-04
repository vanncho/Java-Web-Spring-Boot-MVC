package com.onlinebusreservation.areas.bus.controllers;

import com.onlinebusreservation.areas.bus.exceptions.BusNotFoundException;
import com.onlinebusreservation.areas.bus.models.view.BusDeleteViewModel;
import com.onlinebusreservation.areas.bus.services.BusService;
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

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BusDeleteController.class)
@ActiveProfiles("test")
public class BusDeleteControllerTest {

    private static final Long BUS_ID = 1L;
    private static final Long BUS_ID_INVALID = 10L;
    private static final String BUS_NAME = "Bus Test Name";

    private static final String GET_URL = "/buses/delete/";
    private static final String VIEW_NAME = "base-layout";
    private static final String VIEW_NAME_EXCEPTION = "error/404";
    private static final String MODEL_NAME = "deleteBusModel";

    private static final Integer METHOD_CALL = 1;

    private static final String REDIRECT_VIEW_NAME = "redirect:/buses";
    private static final String REDIRECT_URL = "/buses";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BusService busService;

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

        BusDeleteViewModel bus = new BusDeleteViewModel();
        bus.setBusName(BUS_NAME);

        when(this.busService.findBusToDelete(BUS_ID)).thenReturn(bus);
        when(this.busService.findBusToDelete(BUS_ID_INVALID)).thenThrow(new BusNotFoundException());
    }

    @Test
    public void getDeleteBusById_ShouldReturnBusDeleteViewModel() throws Exception {

        this.mvc
                .perform(get(GET_URL + "/" + BUS_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME))
                .andExpect(model().attribute(MODEL_NAME, hasProperty("busName", is(BUS_NAME))));

        verify(this.busService, times(METHOD_CALL)).findBusToDelete(BUS_ID);
        verifyNoMoreInteractions(this.busService);
    }

    @Test
    public void deleteBusWithValidId_ShouldDeleteAndRedirect() throws Exception {

        this.mvc
                .perform(post(GET_URL + "/" + BUS_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW_NAME))
                .andExpect(redirectedUrl(REDIRECT_URL));
    }

    @Test
    public void deleteBusWithInvalidId_ShouldRedirectToExceptionViewPage() throws Exception {

        this.mvc
                .perform(get(GET_URL + "/" + BUS_ID_INVALID))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME_EXCEPTION));
    }

}