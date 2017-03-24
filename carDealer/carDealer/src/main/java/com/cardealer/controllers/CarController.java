package com.cardealer.controllers;

import com.cardealer.models.binding.car.AddCarModel;
import com.cardealer.models.view.car.CarMakeView;
import com.cardealer.models.view.part.PartListView;
import com.cardealer.models.view.part.PartNamePriceView;
import com.cardealer.services.CarService;
import com.cardealer.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private PartService partService;

    @GetMapping("/{make}")
    public String getAllCarsByMake(@PathVariable("make") String make,
                                   Model model) {

        List<CarMakeView> carMakeViews = this.carService.getAllCarsByMake(make);
        List<String> carSelectionViews = this.carService.getAllCarsByMake();

        model.addAttribute("title", "Car");
        model.addAttribute("carByMake", carSelectionViews);
        model.addAttribute("cars", carMakeViews);

        model.addAttribute("view", "cars/cars-table");
        return "base-layout";
    }

    @GetMapping("/{id}/parts")
    public String getCarsAndParts(Model model,
                                  @PathVariable("id") Long id) {

        CarMakeView carMakeView = this.carService.getById(id);

        List<PartNamePriceView> partNamePriceViews = this.partService.getAllCarParts(id);
        model.addAttribute("title", "Car with parts");

        model.addAttribute("car", carMakeView);
        model.addAttribute("parts", partNamePriceViews);

        model.addAttribute("view", "cars/car-parts-table");
        return "base-layout";
    }

    @GetMapping("")
    public String getAllCarsByMake(Model model) {

        List<CarMakeView> carMakeViews = this.carService.getAllCars();
        List<String> carSelectionViews = this.carService.getAllCarsByMake();

        model.addAttribute("title", "Cars");
        model.addAttribute("cars", carMakeViews);
        model.addAttribute("carByMake", carSelectionViews);

        model.addAttribute("view", "cars/cars-table");
        return "base-layout";
    }

    @GetMapping("/add")
    public String getAddCar(Model model) {

        model.addAttribute("title", "Add Car");
        List<PartListView> partListViews = this.partService.getAll();

        model.addAttribute("parts", partListViews);
        model.addAttribute("view", "cars/car-add");

        return "base-layout";
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute AddCarModel addCarModel) {

        this.carService.addCar(addCarModel);
        return "redirect:/cars";
    }
}
