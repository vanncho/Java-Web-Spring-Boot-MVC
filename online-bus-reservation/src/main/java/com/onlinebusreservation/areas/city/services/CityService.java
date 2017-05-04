package com.onlinebusreservation.areas.city.services;

import com.onlinebusreservation.areas.city.models.view.CityViewModel;

import java.util.List;

public interface CityService {

    CityViewModel getCityByName(String name);

    List<CityViewModel> getAllCities();
}
