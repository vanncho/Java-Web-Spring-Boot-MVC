package com.cardealer.models.view.customer;

import java.util.Date;

public class CustomerOrderedView {

    private Long id;

    private String name;

    private Date birthDate;

    private boolean isYoungDriver;

    public CustomerOrderedView() {
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean getIsYoungDriver() {
        return isYoungDriver;
    }

    public void setIsYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
