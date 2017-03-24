package com.cardealer.models.view.sale;

public class ReviewViewModel {

    private Double discount;

    private String carMakeModel;

    private Long carId;

    private String customer;

    private boolean isYoungDriver;

    private Double carPrice;

    private Double finalCarPrice;

    public ReviewViewModel() {
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getCarMakeModel() {
        return carMakeModel;
    }

    public void setCarMakeModel(String carMakeModel) {
        this.carMakeModel = carMakeModel;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public boolean getIsYoungDriver() {
        return isYoungDriver;
    }

    public void setIsYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public Double getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(Double carPrice) {
        this.carPrice = carPrice;
    }

    public Double getFinalCarPrice() {
        return finalCarPrice;
    }

    public void setFinalCarPrice(Double finalCarPrice) {
        this.finalCarPrice = finalCarPrice;
    }
}
