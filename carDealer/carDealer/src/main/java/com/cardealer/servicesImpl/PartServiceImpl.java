package com.cardealer.servicesImpl;

import com.cardealer.entities.Part;
import com.cardealer.entities.Supplier;
import com.cardealer.mappers.ModelParser;
import com.cardealer.models.binding.part.PartAddModel;
import com.cardealer.models.view.part.PartListView;
import com.cardealer.models.view.part.PartNamePriceView;
import com.cardealer.repositories.PartRepository;
import com.cardealer.repositories.SupplierRepository;
import com.cardealer.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartServiceImpl implements PartService {

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ModelParser modelParser;

    @Override
    public List<PartNamePriceView> getAllCarParts(Long id) {

        List<Part> parts = this.partRepository.getAllCarParts(id);
        List<PartNamePriceView> partNamePriceViews = new ArrayList<>();

        for (Part part : parts) {

            PartNamePriceView partNamePriceView = this.modelParser.convert(part, PartNamePriceView.class);
            partNamePriceViews.add(partNamePriceView);
        }

        return partNamePriceViews;
    }

    @Override
    public void addNewPart(PartAddModel partAddModel) {

        String supplierName = partAddModel.getSupplier();
        Supplier supplier = this.supplierRepository.findByName(supplierName);

        Long quantity = partAddModel.getQuantity();

        if (null == quantity) {

            quantity = 1L;
        }

        Part part = this.modelParser.convert(partAddModel, Part.class);
        part.setSupplier(supplier);
        part.setQuantity(quantity);

        this.partRepository.save(part);
    }

    @Override
    public List<PartListView> getAll() {

        List<Part> parts = this.partRepository.findAll();
        List<PartListView> partListViews = new ArrayList<>();

        for (Part part : parts) {

            PartListView partListView = this.modelParser.convert(part, PartListView.class);
            partListViews.add(partListView);
        }

        return partListViews;
    }

    @Override
    public PartListView getById(Long id) {

        Part part = this.partRepository.getOne(id);
        PartListView partListView = this.modelParser.convert(part, PartListView.class);
        return partListView;
    }

    @Override
    public void edit(PartListView partListView, Long id) {

        Double price = partListView.getPrice();
        Long quantity = partListView.getQuantity();
        this.partRepository.edit(id, price, quantity);
    }

    @Override
    public void delete(Long id) {

        this.partRepository.delete(id);
    }
}
