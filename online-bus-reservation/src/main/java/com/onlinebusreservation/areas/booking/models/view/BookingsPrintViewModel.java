package com.onlinebusreservation.areas.booking.models.view;

public class BookingsPrintViewModel {

    private String userFullName;

    private String busBusName;

    private String originatedFrom;
    
    private String destinationTo;
    
    private String dateOfJourney;

    private String timeFromOrigin;

    private Double totalCost;

    private int[] selectedSeat;

    public BookingsPrintViewModel() {
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getBusBusName() {
        return busBusName;
    }

    public void setBusBusName(String busBusName) {
        this.busBusName = busBusName;
    }

    public String getOriginatedFrom() {
        return originatedFrom;
    }

    public void setOriginatedFrom(String originatedFrom) {
        this.originatedFrom = originatedFrom;
    }

    public String getDestinationTo() {
        return destinationTo;
    }

    public void setDestinationTo(String destinationTo) {
        this.destinationTo = destinationTo;
    }

    public String getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(String dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }

    public String getTimeFromOrigin() {
        return timeFromOrigin;
    }

    public void setTimeFromOrigin(String timeFromOrigin) {
        this.timeFromOrigin = timeFromOrigin;
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
