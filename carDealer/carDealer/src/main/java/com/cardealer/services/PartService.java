package com.cardealer.services;

import com.cardealer.models.binding.part.PartAddModel;
import com.cardealer.models.view.part.PartListView;
import com.cardealer.models.view.part.PartNamePriceView;

import java.util.List;

public interface PartService {

    List<PartNamePriceView> getAllCarParts(Long id);

    void addNewPart(PartAddModel partAddModel);

    List<PartListView> getAll();

    PartListView getById(Long id);

    void edit(PartListView partListView, Long id);

    void delete(Long id);
}
