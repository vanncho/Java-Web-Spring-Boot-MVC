package com.onlinebusreservation.areas.booking.controllers;

import com.onlinebusreservation.areas.booking.exceptions.BookingNotFoundException;
import com.onlinebusreservation.areas.booking.models.view.BookingCancellationViewModel;
import com.onlinebusreservation.areas.booking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/booking")
public class BookingCancellationController {

    private final BookingService bookingService;

    private int[] seats;

    @Autowired
    public BookingCancellationController(BookingService bookingCreateService) {

        this.bookingService = bookingCreateService;
    }

    @GetMapping("/cancellation/{id}")
    public String getMakeCancellation(Model model,
                                      @PathVariable("id") Long id,
                                      @ModelAttribute BookingCancellationViewModel bookingCancellationViewModel) {

        BookingCancellationViewModel booking = this.bookingService.getBookingById(id);
        this.seats = booking.getSelectedSeat();

        model.addAttribute("bookingCancellationViewModel", booking);
        model.addAttribute("view", "booking/cancellation");

        return "base-layout";
    }

    @PostMapping("/cancellation/{id}")
    public String makeCancellation(Model model,
                                   @PathVariable("id") Long id,
                                   @Valid @ModelAttribute BookingCancellationViewModel bookingCancellationViewModel,
                                   BindingResult bindingResult) {

        bookingCancellationViewModel.setSelectedSeat(this.seats);

        if (bindingResult.hasErrors()) {

            model.addAttribute("view", "booking/cancellation");
            return "base-layout";
        }

        this.bookingService.cancelBooking(id, bookingCancellationViewModel);

        return "redirect:/booking/bookings";
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public String bookingNotFound(){

        return "error/404";
    }
}
