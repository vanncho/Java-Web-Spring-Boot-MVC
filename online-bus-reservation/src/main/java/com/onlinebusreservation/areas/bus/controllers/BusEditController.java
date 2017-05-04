package com.onlinebusreservation.areas.bus.controllers;

import com.onlinebusreservation.areas.bus.exceptions.BusNotFoundException;
import com.onlinebusreservation.areas.bus.models.binding.AddBusModel;
import com.onlinebusreservation.areas.bus.services.BusService;
import com.onlinebusreservation.areas.city.models.view.CityViewModel;
import com.onlinebusreservation.areas.city.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/buses")
public class BusEditController {

    private BusService busService;

    private CityService cityService;

    @Autowired
    public BusEditController(BusService busService, CityService cityService) {

        this.busService = busService;
        this.cityService = cityService;
    }

    @GetMapping("/edit/{id}")
    public String getEditBus(@PathVariable("id") Long id,
                             @ModelAttribute AddBusModel addBusModel,
                             Model model) {

        List<CityViewModel> cities = this.cityService.getAllCities();
        AddBusModel bus = this.busService.findBus(id);

        model.addAttribute("title", "Edit Bus");
        model.addAttribute("view", "buses/edit");
        model.addAttribute("addBusModel", bus);
        model.addAttribute("cities", cities);

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editBus(Model model,
                          @PathVariable("id") Long id,
                          @Valid @ModelAttribute AddBusModel addBusModel,
                          BindingResult bindingResult) {

        List<CityViewModel> cities = this.cityService.getAllCities();

        if (bindingResult.hasErrors()) {

            model.addAttribute("bus", addBusModel);
            model.addAttribute("cities", cities);
            model.addAttribute("view", "buses/edit");
            return "base-layout";
        }

        this.busService.updateBus(addBusModel, id);
        return "redirect:/buses";
    }

    @ExceptionHandler(BusNotFoundException.class)
    public String busNotFound() {

        return "error/404";
    }
}
