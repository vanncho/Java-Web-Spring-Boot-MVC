package com.cardealer.servicesImpl;

import com.cardealer.entities.Car;
import com.cardealer.entities.Customer;
import com.cardealer.entities.Sale;
import com.cardealer.mappers.ModelParser;
import com.cardealer.models.binding.sale.SaleReviewModel;
import com.cardealer.models.view.sale.ReviewViewModel;
import com.cardealer.models.view.sale.SaleWithCarView;
import com.cardealer.models.view.sale.SalesView;
import com.cardealer.repositories.CarRepository;
import com.cardealer.repositories.CustomerRepository;
import com.cardealer.repositories.SaleRepository;
import com.cardealer.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelParser modelParser;

    @Override
    public List<SalesView> getAll() {

        List<Sale> sales = this.saleRepository.getAll();
        List<SalesView> salesViews = new ArrayList<>();

        for (Sale sale : sales) {

            SalesView salesView = this.modelParser.convert(sale, SalesView.class);
            salesView.setDiscount(sale.getDiscount() * 100);
            salesViews.add(salesView);
        }

        return salesViews;
    }

    @Override
    public List<SalesView> getAllWithDiscount() {

        List<Sale> sales = this.saleRepository.getAllWithDiscount();
        List<SalesView> salesViews = new ArrayList<>();

        for (Sale sale : sales) {

            SalesView salesView = this.modelParser.convert(sale, SalesView.class);
            salesView.setDiscount(sale.getDiscount() * 100);
            salesViews.add(salesView);
        }

        return salesViews;
    }

    @Override
    public List<SalesView> getAllSalesByDiscount(Integer percent) {

        Double discount = percent / 100.0;
        List<Sale> sales = this.saleRepository.getAllSalesByDiscount(discount);
        List<SalesView> salesViews = new ArrayList<>();

        for (Sale sale : sales) {

            SalesView salesView = this.modelParser.convert(sale, SalesView.class);
            salesView.setDiscount(sale.getDiscount() * 100);
            salesViews.add(salesView);
        }

        return salesViews;
    }

    @Override
    public SaleWithCarView getSaleById(Long id) {

        Sale sale = this.saleRepository.getSaleById(id);
        SaleWithCarView saleWithCarView = this.modelParser.convert(sale, SaleWithCarView.class);
        saleWithCarView.setDiscount(sale.getDiscount() * 100);
        return saleWithCarView;
    }

    @Override
    public ReviewViewModel processSaleReviewModel(SaleReviewModel saleReviewModel) {

        String[] carToken = saleReviewModel.getCar().split("-");
        String carMake = carToken[0];
        String carModel = carToken[2];
        Long carId = Long.valueOf(carToken[1]);

        Car car = this.carRepository.findOne(carId);
        Double carPrice = car.getParts().stream().mapToDouble(x -> x.getPrice()).sum();

        String customerName = saleReviewModel.getCustomer();
        Customer customer = this.customerRepository.getCustomerByName(customerName);

        Double discount = saleReviewModel.getDiscount();
        Double carPriceDiscounted = carPrice - (carPrice * saleReviewModel.getDiscount());

        ReviewViewModel reviewViewModel = new ReviewViewModel();
        reviewViewModel.setCarId(carId);
        reviewViewModel.setCarMakeModel(carMake + " " + carModel);
        reviewViewModel.setIsYoungDriver(customer.getIsYoungDriver());

        reviewViewModel.setDiscount(discount * 100);
        reviewViewModel.setCarPrice(carPrice);

        if (customer.getIsYoungDriver()) {

            discount = 0.05;
        }

        reviewViewModel.setFinalCarPrice((carPriceDiscounted - (carPriceDiscounted * discount)));
        reviewViewModel.setCustomer(customerName);


        return reviewViewModel;
    }

    @Override
    public void addNewSale(ReviewViewModel reviewViewModel, Long cid) {

        Sale sale = new Sale();

        Customer customer = this.customerRepository.getCustomerByName(reviewViewModel.getCustomer());
        sale.setCustomer(customer);

        Double discount = reviewViewModel.getDiscount();

        if (reviewViewModel.getIsYoungDriver()) {

            discount += 0.05;
        }

        sale.setDiscount(discount / 100);

        Car car = this.carRepository.findOne(cid);
        sale.setCar(car);

        this.saleRepository.save(sale);
    }

}
