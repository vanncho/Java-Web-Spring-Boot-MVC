package com.cardealer.servicesImpl;

import com.cardealer.entities.Car;
import com.cardealer.entities.Part;
import com.cardealer.mappers.ModelParser;
import com.cardealer.models.binding.car.AddCarModel;
import com.cardealer.models.view.car.CarMakeView;
import com.cardealer.models.view.car.CarSelectionView;
import com.cardealer.repositories.CarRepository;
import com.cardealer.repositories.PartRepository;
import com.cardealer.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private ModelParser modelParser;

    @Override
    public List<CarMakeView> getAllCarsByMake(String make) {

        List<Car> cars = this.carRepository.getAllCarsByMake(make);
        List<CarMakeView> carMakeViews = new ArrayList<>();

        for (Car car : cars) {

            CarMakeView carMakeView = this.modelParser.convert(car, CarMakeView.class);
            carMakeViews.add(carMakeView);
        }

        return carMakeViews;
    }

    @Override
    public CarMakeView getById(Long id) {

        Car car = this.carRepository.getById(id);
        CarMakeView carMakeView = this.modelParser.convert(car, CarMakeView.class);
        return carMakeView;
    }

    @Override
    public List<CarMakeView> getAllCars() {

        List<Car> cars = this.carRepository.getAllCars();
        List<CarMakeView> carMakeViews = new ArrayList<>();

        for (Car car : cars) {

            CarMakeView carMakeView = this.modelParser.convert(car, CarMakeView.class);
            carMakeViews.add(carMakeView);
        }

        return carMakeViews;
    }

    @Override
    public List<String> getAllCarsByMake() {

        List<String> carNames = this.carRepository.getAllCarsByMake();
        return carNames;
    }

    @Override
    public void addCar(AddCarModel addCarModel) {

        Car car = new Car();
        car.setMake(addCarModel.getMake());
        car.setModel(addCarModel.getModel());
        car.setTravelledDistance(addCarModel.getTravelledDistance());

        List<String> partsAsString = addCarModel.getParts();

        for (String partName : partsAsString) {

            Part part = this.partRepository.getPartByName(partName);
            part.getCars().add(car);
        }

        this.carRepository.save(car);
    }

    @Override
    public List<CarSelectionView> getAllCarsByMakeAndByModel() {

        List<Car> cars = this.carRepository.getAllCars();
        List<CarSelectionView> carSelectionViews = new ArrayList<>();

        for (Car car : cars) {

            CarSelectionView carSelectionView = this.modelParser.convert(car, CarSelectionView.class);
            carSelectionViews.add(carSelectionView);
        }

        return carSelectionViews;
    }
}
