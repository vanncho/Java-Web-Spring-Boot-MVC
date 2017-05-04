package com.onlinebusreservation.areas.bus.models.view;

public class BusBookSelectDateViewModel {

    private String busName;

    private String originatedFromName;

    private String destinationToName;

    private String timeFromOrigin;

    private Double ticketPrice;

    public BusBookSelectDateViewModel() {
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
}
