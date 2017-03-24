package com.cardealer.servicesImpl;

import com.cardealer.entities.Supplier;
import com.cardealer.mappers.ModelParser;
import com.cardealer.models.view.supplier.SupplierAddPartView;
import com.cardealer.models.view.supplier.SupplierPartsView;
import com.cardealer.repositories.SupplierRepository;
import com.cardealer.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ModelParser modelParser;

    @Override
    public List<SupplierAddPartView> findAll() {

        List<Supplier> suppliers = this.supplierRepository.findAll();
        List<SupplierAddPartView> supplierAddPartViews = new ArrayList<>();

        for (Supplier supplier : suppliers) {

            SupplierAddPartView supplierAddPartView = this.modelParser.convert(supplier, SupplierAddPartView.class);
            supplierAddPartViews.add(supplierAddPartView);
        }

        return supplierAddPartViews;
    }

    @Override
    public List<SupplierPartsView> getSuppliers(String param) {

        List<Supplier> suppliers = null;

        if (param.equals("local")) {

            suppliers = this.supplierRepository.getAllLocalSuppliers();
        } else if (param.equals("importers")) {

            suppliers = this.supplierRepository.getAllImportersSuppliers();

        }

        List<SupplierPartsView> supplierPartsViews = new ArrayList<>();

        for (Supplier supplier : suppliers) {

            SupplierPartsView supplierPartsView = this.modelParser.convert(supplier, SupplierPartsView.class);
            supplierPartsView.setPartsSize(supplier.getParts().size());
            supplierPartsViews.add(supplierPartsView);
        }

        return supplierPartsViews;
    }
}
