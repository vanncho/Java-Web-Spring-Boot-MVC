package com.cardealer.services;

import com.cardealer.models.binding.sale.SaleReviewModel;
import com.cardealer.models.view.sale.ReviewViewModel;
import com.cardealer.models.view.sale.SaleWithCarView;
import com.cardealer.models.view.sale.SalesView;

import java.util.List;

public interface SaleService {

    List<SalesView> getAll();

    List<SalesView> getAllWithDiscount();

    List<SalesView> getAllSalesByDiscount(Integer percent);

    SaleWithCarView getSaleById(Long id);

    ReviewViewModel processSaleReviewModel(SaleReviewModel saleReviewModel);

    void addNewSale(ReviewViewModel reviewViewModel, Long cid);
}
