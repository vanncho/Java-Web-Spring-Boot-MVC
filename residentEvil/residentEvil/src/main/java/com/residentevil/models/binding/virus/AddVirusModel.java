package com.residentevil.models.binding.virus;

import com.residentevil.entities.annotations.NotInThePast;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.List;

public class AddVirusModel {

    @NotBlank(message = "Virus name cannot be blank.")
    @Size(min = 3, max = 10, message = "Virus name length must be between 3 and 10 symbols.")
    private String name;

    @NotBlank(message = "Must provide description for the virus.")
    @Size(min = 5, max = 100, message = "Virus description length must be between 5 and 100 symbols.")
    private String description;

    @NotBlank(message = "Must provide side effect.")
    @Size(max = 50, message = "Side effects description must be up to 50 symbols long.")
    private String sideEffects;

    @Pattern(regexp = "^.*[cC]orp.*$", message = "Invalid creator.")
    private String creator;

    private boolean isDeadly;

    private boolean isCurable;

    @NotNull(message = "Mutation cannot be null.")
    private String mutation;

    @NotNull(message = "Enter Turnover Rate.")
    @DecimalMax(value = "100.0", message = "Invalid Turnover Rate.")
    @DecimalMin(value = "0.0", message = "Invalid Turnover Rate.")
    private Double turnoverRate;

    @Range(min = 1, max = 12, message = "Hours until mutation must be between 1 and 12.")
    private Integer hoursUntilTurn;

    private String magnitude;

    @NotInThePast(message = "Released on date should not be in the past.")
    @Pattern(regexp = "^\\d{1,2}\\/\\d{2}\\/\\d{4}$", message = "Enter date in the following format: dd/mm/yyyy")
    private String releasedOn;

    @NotNull(message = "You must select capitals.")
    private List<String> capitals;

    public AddVirusModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean getIsDeadly() {
        return isDeadly;
    }

    public void setIsDeadly(boolean deadly) {
        isDeadly = deadly;
    }

    public boolean getIsCurable() {
        return isCurable;
    }

    public void setIsCurable(boolean curable) {
        isCurable = curable;
    }

    public String getMutation() {
        return mutation;
    }

    public void setMutation(String mutation) {
        this.mutation = mutation;
    }

    public Double getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(Double turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public Integer getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(String releasedOn) {
        this.releasedOn = releasedOn;
    }

    public List<String> getCapitals() {
        return capitals;
    }

    public void setCapitals(List<String> capitals) {
        this.capitals = capitals;
    }
}
