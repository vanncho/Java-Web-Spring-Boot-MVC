package com.onlinebusreservation.areas.booking.models.view;

import com.onlinebusreservation.areas.booking.annotations.BookingCancellationDate;
import com.onlinebusreservation.areas.bus.models.view.BusBookingCancelModel;
import com.onlinebusreservation.areas.user.model.view.UserBookingCancelModel;

@BookingCancellationDate
public class BookingCancellationViewModel {

    private BusBookingCancelModel bus;

    private UserBookingCancelModel user;

    private String dateOfJourney;

    private String bookingDate;

    private Double ticketPrice;

    private int[] selectedSeat;

    public BookingCancellationViewModel() {
    }

    public BusBookingCancelModel getBus() {
        return bus;
    }

    public void setBus(BusBookingCancelModel bus) {
        this.bus = bus;
    }

    public UserBookingCancelModel getUser() {
        return user;
    }

    public void setUser(UserBookingCancelModel user) {
        this.user = user;
    }

    public String getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(String dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int[] getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedSeat(int[] selectedSeat) {
        this.selectedSeat = selectedSeat;
    }
}
