package com.onlinebusreservation.areas.bus.models.view;

import java.util.ArrayList;
import java.util.List;

public class BusBookViewModel {

    private String busName;

    private String originatedFromName;

    private String destinationToName;

    private String timeFromOrigin;

    private Double ticketPrice;
    
    private List<Integer> seats;

    public BusBookViewModel() {

        this.seats = new ArrayList<>();
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

    public String getTimeFromOrigin() {
        return timeFromOrigin;
    }

    public void setTimeFromOrigin(String timeFromOrigin) {
        this.timeFromOrigin = timeFromOrigin;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }
}
