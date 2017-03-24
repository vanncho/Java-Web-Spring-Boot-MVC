package com.cardealer.controllers;

import com.cardealer.models.binding.customer.CustomerAddModel;
import com.cardealer.models.binding.customer.CustomerEditModel;
import com.cardealer.models.view.customer.CustomerOrderedView;
import com.cardealer.models.view.customer.CustomerTotalCarSaleView;
import com.cardealer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all/{param}")
    public String getAllCustomersOrdered(Model model,
                                         @PathVariable("param") String param) {

        List<CustomerOrderedView> customerOrderedViews = null;

        if (param.equals("ascending")) {

            customerOrderedViews = this.customerService.getAllCustomersAcs();
        } else if (param.equals("descending")) {

            customerOrderedViews = this.customerService.getAllCustomersDesc();
        }

        model.addAttribute("title", String.format("Customers by %s", param));
        model.addAttribute("customers", customerOrderedViews);

        model.addAttribute("view", "customers/customers-table");
        return "base-layout";
    }

    @GetMapping("/{id}")
    public String totalSalesByCustomer(Model model,
                                       @PathVariable("id") Long id) {

        CustomerTotalCarSaleView customerTotalCarSaleView = this.customerService.getTotalSalesByCustomer(id);
        model.addAttribute("title", "Customer by id");
        model.addAttribute("customer", customerTotalCarSaleView);

        model.addAttribute("view", "customers/customer-car");
        return "base-layout";
    }

    @GetMapping("/add")
    public String addCustomer(Model model) {

        model.addAttribute("title", "Add new customer");

        model.addAttribute("view", "customers/customer-add");
        return "base-layout";
    }

    @PostMapping("/add")
    public String addNewCustomer(@ModelAttribute CustomerAddModel customerAddModel) {

        this.customerService.addCustomer(customerAddModel);

        return "redirect:/customers/customers/all/ascending";
    }

    @GetMapping("/edit/{id}")
    public String getEditCustomer(Model model,
                                  @PathVariable("id") Long id) {

        model.addAttribute("title", "Edit customer");
        CustomerEditModel customerEditModel = this.customerService.getCustomerById(id);
        model.addAttribute("editCustomer", customerEditModel);
        model.addAttribute("view", "customers/customer-edit");
        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editCustomer(@PathVariable("id") Long id,
                               @RequestParam("name") String name,
                               @RequestParam("birthDate") String birthDate) {

        this.customerService.edit(id, name, birthDate);

        return "redirect:/customers/customers/all/ascending";
    }
}
