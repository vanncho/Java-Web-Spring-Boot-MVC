package com.onlinebusreservation.areas.booking.models.binding;

import com.onlinebusreservation.areas.booking.annotations.NotInThePast;
import com.onlinebusreservation.constants.Errors;
import org.hibernate.validator.constraints.NotEmpty;

public class BookingDateSelectionModel {

    private Long busId;

    private Long userId;

    @NotInThePast
    @NotEmpty(message = Errors.BOOKING_DATE_OF_JOURNEY)
    private String dateOfJourney;

    public BookingDateSelectionModel() {
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
}
