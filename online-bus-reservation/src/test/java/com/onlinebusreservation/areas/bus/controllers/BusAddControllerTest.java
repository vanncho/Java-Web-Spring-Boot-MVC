package com.onlinebusreservation.areas.bus.controllers;

import com.onlinebusreservation.areas.bus.models.binding.AddBusModel;
import com.onlinebusreservation.areas.bus.services.BusService;
import com.onlinebusreservation.areas.city.models.view.CityViewModel;
import com.onlinebusreservation.areas.city.services.CityService;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BusAddController.class)
@ActiveProfiles("test")
public class BusAddControllerTest {

    private static final String CITY_ONE_NAME = "City One";
    private static final String CITY_TWO_NAME = "City Two";

    private static final String BUS_NAME = "Bus Test Name";

    private static final String HOUR = "10";
    private static final String MINUTES = "30";
    private static final String NUMBER_OF_SEATS = "52";
    private static final String TICKET_PRICE = "25";

    private static final String POST_URL = "/buses/add";
    private static final String REDIRECT_VIEW_NAME = "redirect:/buses";
    private static final String REDIRECT_URL = "/buses";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BusService busService;

    @MockBean
    private CityService cityService;

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

        CityViewModel cityOne = new CityViewModel();
        cityOne.setName(CITY_ONE_NAME);

        CityViewModel cityTwo = new CityViewModel();
        cityTwo.setName(CITY_TWO_NAME);

        List<CityViewModel> cities = Arrays.asList(cityOne, cityTwo);
        when(this.cityService.getAllCities()).thenReturn(cities);
    }

    @Test
    public void addBus_ShouldSuccessfullyAddNewBusAndRedirect() throws Exception {

        this.mvc
                .perform(post(POST_URL)
                        .param("busName", BUS_NAME)
                        .param("originatedFromName", CITY_ONE_NAME)
                        .param("destinationToName", CITY_TWO_NAME)
                        .param("hour", HOUR)
                        .param("minutes", MINUTES)
                        .param("numberOfSeats", NUMBER_OF_SEATS)
                        .param("ticketPrice", TICKET_PRICE)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW_NAME))
                .andExpect(redirectedUrl(REDIRECT_URL))
                .andExpect(model().hasNoErrors());

        ArgumentCaptor<AddBusModel> captor = ArgumentCaptor.forClass(AddBusModel.class);
        verify(busService).addNewBus(captor.capture());
        AddBusModel bus = captor.getValue();
        assertEquals(CITY_ONE_NAME, bus.getOriginatedFromName());
    }
}