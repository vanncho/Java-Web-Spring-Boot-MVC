package com.onlinebusreservation.areas.booking.controllers;

import com.onlinebusreservation.areas.booking.exceptions.BookingNotFoundException;
import com.onlinebusreservation.areas.booking.models.view.BookingCancellationViewModel;
import com.onlinebusreservation.areas.booking.services.BookingService;
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

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookingCancellationController.class)
@ActiveProfiles("test")
public class BookingCancellationControllerTest {

    private static final String GET_URL = "/booking/cancellation/{id}";
    private static final String VIEW_NAME = "base-layout";
    private static final String VIEW_NAME_EXCEPTION = "error/404";
    private static final String MODEL_NAME = "bookingCancellationViewModel";

    private static final Long BOOKING_ID = 1L;
    private static final Long BOOKING_ID_INVALID = -1L;
    private static final String DATE_OF_JOURNEY = "2020-05-20";
    private static final String DATE_OF_BOOKING = "2017-03-05";

    private static final String REDIRECT_VIEW_NAME = "redirect:/booking/bookings";
    private static final String REDIRECT_URL = "/booking/bookings";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookingService bookingService;

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

        BookingCancellationViewModel bookingCancellation = new BookingCancellationViewModel();
        bookingCancellation.setDateOfJourney(DATE_OF_JOURNEY);
        bookingCancellation.setBookingDate(DATE_OF_BOOKING);

        when(this.bookingService.getBookingById(BOOKING_ID)).thenReturn(bookingCancellation);
        when(this.bookingService.getBookingById(BOOKING_ID_INVALID)).thenThrow(new BookingNotFoundException());
    }

    @Test
    public void getMakeCancellationWithCorrectDate_ShouldReturnBookingCancellationView() throws Exception {

        this.mvc
                .perform(get(GET_URL, BOOKING_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME))
                .andExpect(model().attribute(MODEL_NAME, hasProperty("dateOfJourney", is((DATE_OF_JOURNEY)))));

        verify(this.bookingService, times(1)).getBookingById(BOOKING_ID);
        verifyNoMoreInteractions(this.bookingService);
    }

    @Test
    public void makeCancellationWithValidBookingId_ShouldDeleteBookingAndRedirect() throws Exception {

        this.mvc
                .perform(post(GET_URL, BOOKING_ID)
                .param("dateOfJourney", DATE_OF_JOURNEY)
                .param("bookingDate", DATE_OF_BOOKING))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW_NAME))
                .andExpect(redirectedUrl(REDIRECT_URL))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void makeCancellationWithInvalidBookingId_ShouldGetPageWithException() throws Exception {

        this.mvc
                .perform(get(GET_URL, BOOKING_ID_INVALID))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME_EXCEPTION));
    }

}