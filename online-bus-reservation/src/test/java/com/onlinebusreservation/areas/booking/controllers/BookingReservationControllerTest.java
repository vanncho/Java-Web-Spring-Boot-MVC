package com.onlinebusreservation.areas.booking.controllers;

import com.onlinebusreservation.areas.booking.exceptions.BookingNotFoundException;
import com.onlinebusreservation.areas.booking.models.view.BookingsPrintViewModel;
import com.onlinebusreservation.areas.booking.services.BookingService;
import com.onlinebusreservation.areas.bus.models.view.BusBookPreViewModel;
import com.onlinebusreservation.areas.bus.models.view.BusBookSelectDateViewModel;
import com.onlinebusreservation.areas.bus.services.BusService;
import com.onlinebusreservation.areas.info.models.view.CompanyInfoViewModel;
import com.onlinebusreservation.areas.info.services.CompanyInfoService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookingReservationController.class)
@ActiveProfiles("test")
public class BookingReservationControllerTest {

    private static final String GET_URL = "/booking/booking-date/{id}";
    private static final String GET_REVIEW_URL = "/booking/review";
    private static final String GET_PRINT_URL = "/booking/print/{id}";

    private static final String VIEW_NAME = "base-layout";
    private static final String VIEW_NAME_EXCEPTION = "error/404";
    private static final String MODEL_NAME = "bus";
    private static final String MODEL_NAME_BOOKING = "booking";
    private static final String MODEL_NAME_COMPANY = "companyInfo";

    private static final String DATE_OF_JOURNEY = "2017-05-05";
    private static final String DATE_OF_JOURNEY_IN_PAST = "2017-04-05";
    private static final Long BUS_ID = 1L;
    private static final String BUS_NAME = "Some bus name";
    private static final String TOWN_NAME = "Dallas";

    private static final String USER_ID = "1";
    private static final Long BOOKING_ID = 1L;
    private static final Long BOOKING_ID_INVALID = -1L;

    private static final String REDIRECT_VIEW_NAME = "redirect:/booking/reservation/";
    private static final String REDIRECT_SAVE_VIEW_NAME = "redirect:/booking/bookings";
    private static final String REDIRECT_URL = "/booking/reservation/";
    private static final String REDIRECT_SAVE_URL = "/booking/bookings";

    private static final Integer METHOD_CALL = 1;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BusService busService;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private CompanyInfoService companyInfoService;

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

        BusBookSelectDateViewModel bus = new BusBookSelectDateViewModel();
        bus.setBusName(BUS_NAME);

        when(this.busService.getSelectedBus(BUS_ID)).thenReturn(bus);

        BusBookPreViewModel busPreview = new BusBookPreViewModel();
        busPreview.setBusName(BUS_NAME);

        when(this.busService.getPreviewBus(BUS_ID)).thenReturn(busPreview);

        BusBookPreViewModel busBookPreViewModel = new BusBookPreViewModel();
        busBookPreViewModel.setBusName(BUS_NAME);

        when(this.busService.getPreviewBus(BUS_ID)).thenReturn(busBookPreViewModel);

        BookingsPrintViewModel printBooking = new BookingsPrintViewModel();
        printBooking.setBusBusName(BUS_NAME);
        CompanyInfoViewModel companyInfoViewModel = new CompanyInfoViewModel();
        companyInfoViewModel.setTown(TOWN_NAME);

        when(this.bookingService.getPrintBooking(BOOKING_ID)).thenReturn(printBooking);
        when(this.bookingService.getPrintBooking(BOOKING_ID_INVALID)).thenThrow(new BookingNotFoundException());
        when(this.companyInfoService.getCompanyInfo()).thenReturn(companyInfoViewModel);
    }

    @Test
    public void getBookingDateSelection_ShouldReturnBusDetailsWithDateReservationModel() throws Exception {

        this.mvc
                .perform(get(GET_URL, BUS_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME))
                .andExpect(model().attribute(MODEL_NAME, hasProperty("busName", is((BUS_NAME)))));

        verify(this.busService, times(METHOD_CALL)).getSelectedBus(BUS_ID);
        verifyNoMoreInteractions(this.busService);
    }

    @Test
    public void bookingDateSelection_ShouldSuccessfullyAddValidDateOfJourney() throws Exception {

        this.mvc
                .perform(post(GET_URL, BUS_ID)
                        .param("dateOfJourney", DATE_OF_JOURNEY))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_VIEW_NAME + BUS_ID))
                .andExpect(redirectedUrl(REDIRECT_URL + BUS_ID))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void bookingDateSelection_ShouldReturnInvalidDateOfJourneyViewModel() throws Exception {

        this.mvc
                .perform(post(GET_URL, BUS_ID)
                        .param("dateOfJourney", DATE_OF_JOURNEY_IN_PAST))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME))
                .andExpect(model().hasErrors());
    }

    @Test
    public void bookingReviewSave_ShouldSuccessfullySaveReservation() throws Exception {

        this.mvc
                .perform(post(GET_REVIEW_URL)
                        .param("userId", USER_ID)
                        .param("dateOfJourney", DATE_OF_JOURNEY))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_SAVE_VIEW_NAME))
                .andExpect(redirectedUrl(REDIRECT_SAVE_URL))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void getBookingPrint_ShouldSuccessfullyGetBookingPrintView() throws Exception {

        this.mvc
                .perform(get(GET_PRINT_URL, BOOKING_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME))
                .andExpect(model().attribute(MODEL_NAME_BOOKING, hasProperty("busBusName", is((BUS_NAME)))))
                .andExpect(model().attribute(MODEL_NAME_COMPANY, hasProperty("town", is((TOWN_NAME)))));

        verify(this.bookingService, times(METHOD_CALL)).getPrintBooking(BOOKING_ID);
        verifyNoMoreInteractions(this.bookingService);

        verify(this.companyInfoService, times(METHOD_CALL)).getCompanyInfo();
        verifyNoMoreInteractions(this.companyInfoService);
    }

    @Test
    public void getBookingPrintWithInvalidId_ShouldGetPageWithException() throws Exception {

        this.mvc
                .perform(get(GET_PRINT_URL, BOOKING_ID_INVALID))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME_EXCEPTION));
    }

}