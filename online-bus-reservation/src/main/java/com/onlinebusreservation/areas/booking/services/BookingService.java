package com.onlinebusreservation.areas.booking.services;

import com.onlinebusreservation.areas.booking.exceptions.BookingNotFoundException;
import com.onlinebusreservation.areas.booking.models.binding.BookingRegistrationModel;
import com.onlinebusreservation.areas.booking.models.view.BookingCancellationViewModel;
import com.onlinebusreservation.areas.booking.models.view.BookingsByUserViewModel;
import com.onlinebusreservation.areas.booking.models.view.BookingsPrintViewModel;

import java.util.List;

public interface BookingService {

    void create(BookingRegistrationModel bookingRegistrationModel);

    List<BookingsByUserViewModel> getBookingsByUser(long userId);

    BookingCancellationViewModel getBookingById(Long id) throws BookingNotFoundException;

    void cancelBooking(Long id, BookingCancellationViewModel bookingCancellationViewModel);

    void deleteBooking(Long bookingId, BookingsByUserViewModel bookingsByUserViewModel);

    BookingsPrintViewModel getPrintBooking(Long id);
}
