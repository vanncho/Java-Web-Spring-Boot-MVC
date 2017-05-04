package com.onlinebusreservation.areas.bus.models.view;

public class BusSearchModel {

    private Long id;

    private String busName;

    private String timeFromOrigin;

    private Double ticketPrice;

    private Integer freeSeats;

    public BusSearchModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
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

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
    }
}
