package com.cardealer.models.view.sale;

public class SaleWithCarView {

    private Double discount;

    private String carMake;

    private String carModel;

    private String customerName;

    public SaleWithCarView() {
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
