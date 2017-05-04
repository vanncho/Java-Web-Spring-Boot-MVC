package com.onlinebusreservation.areas.booking.models.view;

import com.onlinebusreservation.areas.bus.models.view.BusBookingDeleteModel;
import com.onlinebusreservation.areas.user.model.view.UserBookingCancelModel;

public class BookingsByUserViewModel {

    private Long id;

    private UserBookingCancelModel user;

    private BusBookingDeleteModel bus;

    private Double totalCost;

    private Integer seatsCount;

    public BookingsByUserViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserBookingCancelModel getUser() {
        return user;
    }

    public void setUser(UserBookingCancelModel user) {
        this.user = user;
    }

    public BusBookingDeleteModel getBus() {
        return bus;
    }

    public void setBus(BusBookingDeleteModel bus) {
        this.bus = bus;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(Integer seatsCount) {
        this.seatsCount = seatsCount;
    }
}
