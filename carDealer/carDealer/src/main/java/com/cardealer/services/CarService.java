package com.cardealer.services;

import com.cardealer.models.binding.car.AddCarModel;
import com.cardealer.models.view.car.CarMakeView;
import com.cardealer.models.view.car.CarSelectionView;

import java.util.List;

public interface CarService {

    List<CarMakeView> getAllCarsByMake(String make);

    CarMakeView getById(Long id);

    List<CarMakeView> getAllCars();

    List<String> getAllCarsByMake();

    void addCar(AddCarModel addCarModel);

    List<CarSelectionView> getAllCarsByMakeAndByModel();
}
