package com.onlinebusreservation.areas.info.models.binding;

import com.onlinebusreservation.constants.Errors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CompanyInfoEditModel {

    private Long id;

    @NotNull(message = Errors.COMPANY_NAME)
    @Size(min = 5, message = Errors.COMPANY_NAME_LENGTH)
    private String companyName;

    @NotNull(message = Errors.TOWN_NAME)
    @Size(min = 3, message = Errors.TOWN_NAME_LENGTH)
    private String town;

    @NotNull(message = Errors.COMPANY_ADDRESS)
    @Size(min = 10, message = Errors.COMPANY_ADDRESS_LENGTH)
    private String address;

    @NotNull(message = Errors.COMPANY_TELEPHONE)
    private String phoneNumber;

    public CompanyInfoEditModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
