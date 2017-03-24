package com.cardealer.models.binding.car;

import java.util.ArrayList;
import java.util.List;

public class AddCarModel {

    private String make;

    private String model;

    private Long travelledDistance;

    private List<String> parts;

    public AddCarModel() {

        this.parts = new ArrayList<>();
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<String> getParts() {
        return parts;
    }

    public void setParts(List<String> parts) {
        this.parts = parts;
    }
}
