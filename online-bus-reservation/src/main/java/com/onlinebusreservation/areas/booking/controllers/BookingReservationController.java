package com.onlinebusreservation.areas.booking.controllers;

import com.onlinebusreservation.areas.booking.exceptions.BookingNotFoundException;
import com.onlinebusreservation.areas.booking.models.binding.BookingDateSelectionModel;
import com.onlinebusreservation.areas.booking.models.binding.BookingRegistrationModel;
import com.onlinebusreservation.areas.booking.models.view.BookingsPrintViewModel;
import com.onlinebusreservation.areas.booking.services.BookingService;
import com.onlinebusreservation.areas.bus.models.view.BusBookPreViewModel;
import com.onlinebusreservation.areas.bus.models.view.BusBookSelectDateViewModel;
import com.onlinebusreservation.areas.bus.models.view.BusBookViewModel;
import com.onlinebusreservation.areas.bus.services.BusService;
import com.onlinebusreservation.areas.info.models.view.CompanyInfoViewModel;
import com.onlinebusreservation.areas.info.services.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/booking")
public class BookingReservationController {

    @Autowired
    private BusService busService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CompanyInfoService companyInfoService;

    private final BookingRegistrationModel previewBooking;

    public BookingReservationController() {

        this.previewBooking = new BookingRegistrationModel();
    }

    @GetMapping("/booking-date/{id}")
    public String getBookingDateSelection(Model model,
                                          @PathVariable("id") Long id,
                                          @ModelAttribute BookingDateSelectionModel bookingDateSelectionModel) {

        BusBookSelectDateViewModel bus = this.busService.getSelectedBus(id);
        this.previewBooking.setBusId(id);

        model.addAttribute("title", "Select Booking Date");
        model.addAttribute("bus", bus);
        model.addAttribute("busId", id);
        model.addAttribute("view", "booking/booking-date");

        return "base-layout";
    }

    @PostMapping("/booking-date/{id}")
    public String bookingDateSelection(Model model,
                                       @PathVariable("id") Long id,
                                       @Valid @ModelAttribute BookingDateSelectionModel bookingDateSelectionModel,
                                       BindingResult bindingResult) {

        this.previewBooking.setBusId(id);
        this.previewBooking.setDateOfJourney(bookingDateSelectionModel.getDateOfJourney());

        if (bindingResult.hasErrors()) {

            BusBookSelectDateViewModel bus = this.busService.getSelectedBus(id);
            model.addAttribute("bus", bus);
            model.addAttribute("view", "booking/booking-date");
            return "base-layout";
        }

        return "redirect:/booking/reservation/" + id;
    }

    @GetMapping("/reservation/{id}")
    public String getMakeBooking(Model model,
                                 @PathVariable("id") Long id,
                                 @ModelAttribute BookingRegistrationModel bookingRegistrationModel,
                                 HttpSession session) {

        if (null == this.previewBooking.getDateOfJourney()) {
            this.previewBooking.setDateOfJourney((String)session.getAttribute("chosenDate"));
        }

        this.previewBooking.setBusId(id);
        BusBookViewModel bus = this.busService.getBookBus(id, this.previewBooking.getDateOfJourney());

        model.addAttribute("title", "Bus Booking");
        model.addAttribute("dateOfJourney", this.previewBooking.getDateOfJourney());
        model.addAttribute("bus", bus);
        model.addAttribute("view", "booking/reservation");

        return "base-layout";
    }

    @PostMapping("/reservation/{id}")
    public String makeBooking(Model model,
                              @PathVariable("id") Long id,
                              @Valid @ModelAttribute BookingRegistrationModel bookingRegistrationModel,
                              BindingResult bindingResult) {

        this.previewBooking.setTotalCost(bookingRegistrationModel.getTotalCost());
        this.previewBooking.setSelectedSeat(bookingRegistrationModel.getSelectedSeat());

        if (bindingResult.hasErrors()) {

            BusBookViewModel bus = this.busService.getBookBus(id, this.previewBooking.getDateOfJourney());
            model.addAttribute("bus", bus);
            model.addAttribute("dateOfJourney", this.previewBooking.getDateOfJourney());
            model.addAttribute("totalCost", bookingRegistrationModel.getTotalCost());
            model.addAttribute("view", "booking/reservation");
            return "base-layout";
        }

        return "redirect:/booking/review";
    }

    @GetMapping("/review")
    public String getBookingReview(Model model) {

        BusBookPreViewModel bus = this.busService.getPreviewBus(this.previewBooking.getBusId());
        Arrays.sort(this.previewBooking.getSelectedSeat());

        model.addAttribute("title", "Review Booking");
        model.addAttribute("bus", bus);
        model.addAttribute("booking", this.previewBooking);

        model.addAttribute("view", "booking/review");
        return "base-layout";
    }

    @PostMapping("/review")
    public String bookingReviewSave(@RequestParam("userId") Long userId,
                                    @RequestParam("dateOfJourney") String dateOfJourney) {

        BookingRegistrationModel booking = new BookingRegistrationModel();
        booking.setBusId(this.previewBooking.getBusId());
        booking.setUserId(userId);
        booking.setTotalCost(this.previewBooking.getTotalCost());
        booking.setDateOfJourney(dateOfJourney);
        booking.setSelectedSeat(this.previewBooking.getSelectedSeat());

        this.bookingService.create(booking);
        return "redirect:/booking/bookings";
    }

    @GetMapping("/print/{id}")
    public String getBookingPrint(Model model,
                                  @PathVariable("id") Long id) {

        BookingsPrintViewModel bookingPrintViewModel = this.bookingService.getPrintBooking(id);
        CompanyInfoViewModel companyInfo = this.companyInfoService.getCompanyInfo();

        model.addAttribute("title", "Print Booking");
        model.addAttribute("companyInfo", companyInfo);
        model.addAttribute("booking", bookingPrintViewModel);
        model.addAttribute("view", "booking/print");
        return "base-layout";
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public String bookingNotFound(){

        return "error/404";
    }
}
