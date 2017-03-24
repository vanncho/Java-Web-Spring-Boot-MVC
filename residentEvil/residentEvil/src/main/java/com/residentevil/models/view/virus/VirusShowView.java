package com.residentevil.models.view.virus;

import com.residentevil.entities.enumerations.Magnitude;

import java.util.Date;

public class VirusShowView {

    private Long id;

    private String name;

    private Magnitude magnitude;

    private Date releasedOn;

    public VirusShowView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Magnitude getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    public Date getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(Date releasedOn) {
        this.releasedOn = releasedOn;
    }
}
