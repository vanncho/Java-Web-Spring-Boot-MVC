package com.onlinebusreservation.areas.booking.models.binding;

import com.onlinebusreservation.constants.Errors;
import org.hibernate.validator.constraints.NotEmpty;

public class BookingRegistrationModel {

    private Long busId;

    private Long userId;

    private String dateOfJourney;

    private Double totalCost;

    @NotEmpty(message = Errors.BOOKING_SEAT_SELECT)
    private int[] selectedSeat;

    public BookingRegistrationModel() {
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(String dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public int[] getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedSeat(int[] selectedSeat) {
        this.selectedSeat = selectedSeat;
    }
}
