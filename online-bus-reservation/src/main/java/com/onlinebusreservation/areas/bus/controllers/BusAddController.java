package com.onlinebusreservation.areas.bus.controllers;

import com.onlinebusreservation.areas.bus.models.binding.AddBusModel;
import com.onlinebusreservation.areas.bus.services.BusService;
import com.onlinebusreservation.areas.city.models.view.CityViewModel;
import com.onlinebusreservation.areas.city.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/buses")
public class BusAddController {

    private BusService busService;

    private CityService cityService;

    @Autowired
    public BusAddController(BusService busService, CityService cityService) {

        this.busService = busService;
        this.cityService = cityService;
    }

    @GetMapping("/add")
    public String getAddBus(Model model,
                            @ModelAttribute AddBusModel addBusModel) {

        List<CityViewModel> cities = this.cityService.getAllCities();

        model.addAttribute("title", "Add New Bus");
        model.addAttribute("cities", cities);
        model.addAttribute("view", "buses/add");
        return "base-layout";
    }

    @PostMapping("/add")
    public String addBus(Model model,
                         @Valid @ModelAttribute AddBusModel addBusModel,
                         BindingResult bindingResult) {

        List<CityViewModel> cities = this.cityService.getAllCities();;

        if (bindingResult.hasErrors()) {

            model.addAttribute("title", "Add New Bus");
            model.addAttribute("cities", cities);
            model.addAttribute("view", "buses/add");
            return "base-layout";
        }

        this.busService.addNewBus(addBusModel);
        return "redirect:/buses";
    }
}
