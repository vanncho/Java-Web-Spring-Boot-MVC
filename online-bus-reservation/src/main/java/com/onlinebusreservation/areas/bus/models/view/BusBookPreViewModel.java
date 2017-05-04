package com.onlinebusreservation.areas.bus.models.view;

public class BusBookPreViewModel {

    private String busName;

    private String originatedFromName;

    private String destinationToName;

    public BusBookPreViewModel() {
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
}
