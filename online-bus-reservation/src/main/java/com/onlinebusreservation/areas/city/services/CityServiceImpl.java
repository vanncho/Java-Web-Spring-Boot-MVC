package com.onlinebusreservation.areas.city.services;

import com.onlinebusreservation.areas.city.entities.City;
import com.onlinebusreservation.areas.city.services.CityService;
import com.onlinebusreservation.mappers.ModelParser;
import com.onlinebusreservation.areas.city.models.view.CityViewModel;
import com.onlinebusreservation.areas.city.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;

    private final ModelParser modelParser;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, ModelParser modelParser) {

        this.cityRepository = cityRepository;
        this.modelParser = modelParser;
    }

    @Override
    public CityViewModel getCityByName(String name) {

        City city = this.cityRepository.findOneByName(name);
        CityViewModel cityViewModel = this.modelParser.convert(city, CityViewModel.class);
        return cityViewModel;
    }

    @Override
    public List<CityViewModel> getAllCities() {

        List<City> cities = this.cityRepository.findAll();
        List<CityViewModel> cityViewModels = new ArrayList<>();

        for (City city : cities) {

            CityViewModel cityViewModel = this.modelParser.convert(city, CityViewModel.class);
            cityViewModels.add(cityViewModel);
        }

        return cityViewModels;
    }
}
