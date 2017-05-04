package com.onlinebusreservation.areas.booking.controllers;

import com.onlinebusreservation.areas.booking.models.view.BookingsByUserViewModel;
import com.onlinebusreservation.areas.booking.services.BookingService;
import com.onlinebusreservation.areas.user.services.UserFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    private final UserFinder userFinder;

    @Autowired
    public BookingController(BookingService bookingService, UserFinder userFinder) {

        this.bookingService = bookingService;
        this.userFinder = userFinder;
    }

    @GetMapping("/bookings")
    public String getUserBookings(Model model,
                                  Authentication authentication) {

        Long userId = this.userFinder.userId(authentication);
        List<BookingsByUserViewModel> bookings = this.bookingService.getBookingsByUser(userId);

        model.addAttribute("bookings", bookings);
        model.addAttribute("view", "booking/bookings");

        return "base-layout";
    }
}
