package com.cardealer.services;

import com.cardealer.models.view.supplier.SupplierAddPartView;
import com.cardealer.models.view.supplier.SupplierPartsView;

import java.util.List;

public interface SupplierService {

    List<SupplierAddPartView> findAll();

    List<SupplierPartsView> getSuppliers(String param);
}
