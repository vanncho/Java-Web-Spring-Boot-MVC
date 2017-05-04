package com.onlinebusreservation.interceptors;

import com.onlinebusreservation.areas.booking.models.view.BookingsByUserViewModel;
import com.onlinebusreservation.areas.booking.services.BookingService;
import com.onlinebusreservation.areas.user.entities.User;
import com.onlinebusreservation.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ValidBookingCheckInterceptor extends HandlerInterceptorAdapter {

    private final BookingService bookingService;

    @Autowired
    public ValidBookingCheckInterceptor(BookingService bookingService) {

        this.bookingService = bookingService;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<BookingsByUserViewModel> bookings = this.bookingService.getBookingsByUser(user.getId());

        for (BookingsByUserViewModel booking : bookings) {

            boolean toDelete = this.mustBeDeleted(booking);

            if (toDelete) {

                this.bookingService.deleteBooking(booking.getId(), booking);
            }
        }

        return true;
    }

    private boolean mustBeDeleted(BookingsByUserViewModel booking) {

        DateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        LocalDate localDate = LocalDate.now();
        String today = String.valueOf(localDate);
        String dateOfJourneyString = booking.getBus().getDateOfJourney();

        Date now = null;
        Date dateOfJourney = null;

        try {
            now = format.parse(today);
            dateOfJourney = format.parse(dateOfJourneyString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long duration  = dateOfJourney.getTime() - now.getTime();

        long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

        return diffInHours < 0;
    }
}
