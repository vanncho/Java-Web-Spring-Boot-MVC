package com.cardealer.services;

import com.cardealer.models.binding.customer.CustomerAddModel;
import com.cardealer.models.binding.customer.CustomerEditModel;
import com.cardealer.models.view.customer.CustomerAddSaleView;
import com.cardealer.models.view.customer.CustomerOrderedView;
import com.cardealer.models.view.customer.CustomerTotalCarSaleView;

import java.util.List;

public interface CustomerService {

    List<CustomerOrderedView> getAllCustomersAcs();

    List<CustomerOrderedView> getAllCustomersDesc();

    CustomerTotalCarSaleView getTotalSalesByCustomer(Long id);

    void addCustomer(CustomerAddModel customerAddModel);

    CustomerEditModel getCustomerById(Long id);

    void edit(Long id, String name, String birthDate);

    List<CustomerAddSaleView> getAllCustomersByName();
}
