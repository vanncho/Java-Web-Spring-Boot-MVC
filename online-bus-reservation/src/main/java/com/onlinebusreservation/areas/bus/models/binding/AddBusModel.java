package com.onlinebusreservation.areas.bus.models.binding;

import com.onlinebusreservation.areas.city.entities.annotations.CitiesMatching;
import com.onlinebusreservation.constants.Errors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@CitiesMatching
public class AddBusModel {

    @NotNull(message = Errors.BUS_NAME)
    @Size(min = 5, message = Errors.BUS_NAME_LENGTH)
    private String busName;

    @Size(min = 1, message = Errors.BUS_ORIGIN)
    private String originatedFromName;

    @Size(min = 1, message = Errors.BUS_DESTINATION)
    private String destinationToName;

    @NotNull(message = Errors.BUS_START_HOUR)
    @Range(min = 0, max = 23, message = Errors.BUS_INVALID_HOUR)
    private Integer hour;

    @NotNull(message = Errors.BUS_START_MINUTES)
    @Range(min = 0, max = 45, message = Errors.BUS_INVALID_MINUTES)
    private Integer minutes;

    @NotNull(message = Errors.BUS_SEATS_NUMBER)
    @Range(min = 1, max = 52, message = Errors.BUS_SEATS_NUMBER)
    private Integer numberOfSeats;

    @NotNull(message = Errors.BUS_TICKET_PRICE)
    private Double ticketPrice;

    public AddBusModel() {
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getOriginatedFromName() {
        return originatedFromName;
    }

    public void setOriginatedFromName(String originatedFromName) {
        this.originatedFromName = originatedFromName;
    }

    public String getDestinationToName() {
        return destinationToName;
    }

    public void setDestinationToName(String destinationToName) {
        this.destinationToName = destinationToName;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
