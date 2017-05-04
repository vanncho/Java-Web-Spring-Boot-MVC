package com.onlinebusreservation.areas.bus.controllers;

import com.onlinebusreservation.areas.bus.models.view.BusListModel;
import com.onlinebusreservation.areas.bus.services.BusService;
import com.onlinebusreservation.interceptors.UserUnreadMessagesInterceptor;
import com.onlinebusreservation.interceptors.ValidBookingCheckInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BusController.class)
@ActiveProfiles("test")
public class BusControllerTest {

    private static final Long BUS_ONE_ID = 1L;
    private static final String BUS_ONE_NAME = "Bus One";

    private static final Long BUS_TWO_ID = 2L;
    private static final String BUS_TWO_NAME = "Bus Two";

    private static final Integer COLLECTION_SIZE = 2;
    private static final Integer METHOD_CALL = 1;
    
    private static final String GET_URL = "/buses";
    private static final String VIEW_NAME = "base-layout";
    private static final String MODEL_NAME = "buses";

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

        BusListModel busOne = new BusListModel();
        busOne.setId(BUS_ONE_ID);
        busOne.setBusName(BUS_ONE_NAME);

        BusListModel busTwo = new BusListModel();
        busTwo.setId(BUS_TWO_ID);
        busTwo.setBusName(BUS_TWO_NAME);

        List<BusListModel> buses = Arrays.asList(busOne, busTwo);
        when(this.busService.getAllBuses()).thenReturn(buses);
    }

    @Test
    public void getBuses_ShouldReturnAllBusesFromDataBase() throws Exception {

        this.mvc.perform(
                get(GET_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME))
                .andExpect(model().attribute(MODEL_NAME, hasSize(COLLECTION_SIZE)));

        verify(this.busService, times(METHOD_CALL)).getAllBuses();
        verifyNoMoreInteractions(this.busService);
    }
}