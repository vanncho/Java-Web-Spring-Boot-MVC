package com.onlinebusreservation.areas.bus.controllers;

import com.onlinebusreservation.areas.bus.exceptions.BusNotFoundException;
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

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BusEditController.class)
@ActiveProfiles("test")
public class BusEditControllerTest {

    private static final String CITY_EDIT_ONE = "City One";
    private static final String CITY_EDIT_TWO = "City Two";

    private static final Long BUS_ID = 1L;
    private static final Long BUS_ID_INVALID = -1L;
    private static final String BUS_EDIT_NAME = "Bus Edit Name";
    private static final Integer HOUR = 10;
    private static final Integer MINUTES = 30;
    private static final Integer NUMBER_OF_SEATS = 52;
    private static final Double BUS_TICKET_PRICE = 25.05;

    private static final String GET_URL = "/buses/edit";
    private static final String VIEW_NAME = "base-layout";
    private static final String VIEW_NAME_EXCEPTION = "error/404";
    private static final String MODEL_NAME = "addBusModel";

    private static final String REDIRECT_VIEW_NAME = "redirect:/buses";
    private static final String REDIRECT_URL = "/buses";

    private static final Integer METHOD_CALL = 1;

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

        CityViewModel firstCity = new CityViewModel();
        firstCity.setName(CITY_EDIT_ONE);

        CityViewModel secondCity = new CityViewModel();
        firstCity.setName(CITY_EDIT_TWO);

        List<CityViewModel> cities = Arrays.asList(firstCity, secondCity);
        when(this.cityService.getAllCities()).thenReturn(cities);

        AddBusModel bus = new AddBusModel();
        bus.setBusName(BUS_EDIT_NAME);
        bus.setOriginatedFromName(CITY_EDIT_ONE);
        bus.setDestinationToName(CITY_EDIT_TWO);
        bus.setHour(HOUR);
        bus.setMinutes(MINUTES);
        bus.setNumberOfSeats(NUMBER_OF_SEATS);
        bus.setTicketPrice(BUS_TICKET_PRICE);

        when(this.busService.findBus(BUS_ID)).thenReturn(bus);
        when(this.busService.findBus(BUS_ID_INVALID)).thenThrow(new BusNotFoundException());
    }

    @Test
    public void getEditBusById_ShouldReturnAddBusViewModel() throws Exception {

        this.mvc
                .perform(get(GET_URL + "/" + BUS_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME))
                .andExpect(model().attribute(MODEL_NAME, hasProperty("busName", is(BUS_EDIT_NAME))))
                .andExpect(model().attribute(MODEL_NAME, hasProperty("ticketPrice", is(BUS_TICKET_PRICE))));

        verify(this.busService, times(METHOD_CALL)).findBus(BUS_ID);
        verifyNoMoreInteractions(this.busService);
    }

    @Test
    public void editBusWithValidId_ShouldUpdateBusAndRedirect() throws Exception {

        this.mvc
                .perform(post(GET_URL + "/" + BUS_ID)
                        .param("busName", BUS_EDIT_NAME)
                        .param("originatedFromName", CITY_EDIT_ONE)
                        .param("destinationToName", CITY_EDIT_TWO)
                        .param("hour", String.valueOf(HOUR))
                        .param("minutes", String.valueOf(MINUTES))
                        .param("numberOfSeats", String.valueOf(NUMBER_OF_SEATS))
                        .param("ticketPrice", String.valueOf(BUS_TICKET_PRICE))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW_NAME))
                .andExpect(redirectedUrl(REDIRECT_URL));
    }

    @Test
    public void editBusWithInvalidId_ShouldRedirectToExceptionViewPage() throws Exception {

        this.mvc
                .perform(get(GET_URL + "/" + BUS_ID_INVALID))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME_EXCEPTION));
    }

}