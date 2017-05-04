package com.residentevil.services;

import com.residentevil.models.binding.virus.AddVirusModel;
import com.residentevil.models.binding.virus.UpdateVirusModel;
import com.residentevil.models.view.virus.EditVirusView;
import com.residentevil.models.view.virus.VirusShowView;

import java.util.List;

public interface VirusService {

    List<VirusShowView> getAllViruses();

    void addVirus(AddVirusModel addVirusModel);

    EditVirusView getById(Long id);

    void updateVirus(Long id, UpdateVirusModel updateVirusModel);

    void deleteVirus(Long id);

    String getGeoData();
}
