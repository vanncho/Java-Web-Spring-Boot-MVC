package com.residentevil.services;

import com.residentevil.entities.Capital;
import com.residentevil.mappers.ModelParser;
import com.residentevil.models.view.capital.CapitalsAddVirusView;
import com.residentevil.repositories.CapitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CapitalServiceImpl implements CapitalService {

    @Autowired
    private CapitalRepository capitalRepository;

    @Autowired
    private ModelParser modelParser;

    @Override
    public List<CapitalsAddVirusView> getCapitalNames() {

        List<Capital> capitals = this.capitalRepository.findAll();
        List<CapitalsAddVirusView> capitalsAddVirusViews = new ArrayList<>();

        for (Capital capital : capitals) {

            CapitalsAddVirusView capitalsAddVirusView = this.modelParser.convert(capital, CapitalsAddVirusView.class);
            capitalsAddVirusViews.add(capitalsAddVirusView);
        }

        return capitalsAddVirusViews;
    }
}
