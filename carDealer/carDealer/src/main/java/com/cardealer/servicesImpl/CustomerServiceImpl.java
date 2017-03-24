package com.cardealer.servicesImpl;

import com.cardealer.entities.Customer;
import com.cardealer.mappers.ModelParser;
import com.cardealer.models.binding.customer.CustomerAddModel;
import com.cardealer.models.binding.customer.CustomerEditModel;
import com.cardealer.models.view.customer.CustomerAddSaleView;
import com.cardealer.models.view.customer.CustomerOrderedView;
import com.cardealer.models.view.customer.CustomerTotalCarSaleView;
import com.cardealer.repositories.CustomerRepository;
import com.cardealer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelParser modelParser;

    @Override
    public List<CustomerOrderedView> getAllCustomersAcs() {

        //List<Customer> customers = this.customerRepository.findAllByOrderByBirthDateAsc();
        List<Customer> customers = this.customerRepository.getAllByOrderByBirthDateAscIsYoungDriverAsc();
        List<CustomerOrderedView> customerOrderedViews = new ArrayList<>();

        for (Customer customer : customers) {

            CustomerOrderedView customerOrderedView = this.modelParser.convert(customer, CustomerOrderedView.class);
            customerOrderedViews.add(customerOrderedView);
        }

        return customerOrderedViews;
    }

    @Override
    public List<CustomerOrderedView> getAllCustomersDesc() {

        List<Customer> customers = this.customerRepository.getAllByOrderByBirthDateDescIsYoungDriverAsc();
        List<CustomerOrderedView> customerOrderedViews = new ArrayList<>();

        for (Customer customer : customers) {

            CustomerOrderedView customerOrderedView = this.modelParser.convert(customer, CustomerOrderedView.class);
            customerOrderedViews.add(customerOrderedView);
        }

        return customerOrderedViews;
    }

    @Override
    public CustomerTotalCarSaleView getTotalSalesByCustomer(Long id) {

        List<Object[]> object = this.customerRepository.getTotalSalesByCustomer(id);

        CustomerTotalCarSaleView customerTotalCarSaleView = new CustomerTotalCarSaleView();

        for (Object[] objects : object) {

            customerTotalCarSaleView.setName((String) objects[0]);
            BigInteger bigIntegerValue = (BigInteger) objects[1];
            customerTotalCarSaleView.setBoughtCars(Integer.valueOf(bigIntegerValue.toString()));
            customerTotalCarSaleView.setSpentMoney((Double) objects[2]);

        }

        return customerTotalCarSaleView;
    }

    @Override
    public void addCustomer(CustomerAddModel customerAddModel) {

        Customer customer = new Customer();
        customer.setName(customerAddModel.getName());

        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date customerBirthDate = null;

        try {
            customerBirthDate = format.parse(customerAddModel.getBirthDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean isYoung = this.isYoungDriver(customerBirthDate);

        customer.setIsYoungDriver(isYoung);
        customer.setBirthDate(customerBirthDate);
        this.customerRepository.save(customer);
    }

    @Override
    public CustomerEditModel getCustomerById(Long id) {

        Customer customer = this.customerRepository.getOne(id);
        CustomerEditModel customerEditModel = this.modelParser.convert(customer, CustomerEditModel.class);
        return customerEditModel;
    }

    @Override
    public void edit(Long id, String name, String birthDate) {

        DateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH);
        Date customerBirthDate = null;

        try {
            customerBirthDate = format.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean isYoung = this.isYoungDriver(customerBirthDate);
        this.customerRepository.edit(id, name, customerBirthDate, isYoung);
    }

    @Override
    public List<CustomerAddSaleView> getAllCustomersByName() {

        List<Customer> customers = this.customerRepository.findAll();
        List<CustomerAddSaleView> customerAddSaleViews = new ArrayList<>();

        for (Customer customer : customers) {

            CustomerAddSaleView customerAddSaleView = this.modelParser.convert(customer, CustomerAddSaleView.class);
            customerAddSaleViews.add(customerAddSaleView);
        }

        return customerAddSaleViews;
    }

    private boolean isYoungDriver(Date customerBirthDate) {

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(customerBirthDate);
        calendar2.setTime(new Date());

        int yearCustomer = calendar1.get(Calendar.YEAR);
        int yearNow = calendar2.get(Calendar.YEAR);

        if (yearNow - yearCustomer < 20) {

            return true;

        } else {

            return false;
        }
    }
}
