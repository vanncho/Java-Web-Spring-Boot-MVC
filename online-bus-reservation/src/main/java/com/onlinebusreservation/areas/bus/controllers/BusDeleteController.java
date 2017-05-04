package com.onlinebusreservation.areas.bus.controllers;

import com.onlinebusreservation.areas.bus.exceptions.BusNotFoundException;
import com.onlinebusreservation.areas.bus.models.view.BusDeleteViewModel;
import com.onlinebusreservation.areas.bus.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/buses")
public class BusDeleteController {

    private BusService busService;

    @Autowired
    public BusDeleteController(BusService busService) {

        this.busService = busService;
    }

    @GetMapping("/delete/{id}")
    public String getDeleteBus(@PathVariable("id") Long id,
                               Model model) {

        BusDeleteViewModel bus = this.busService.findBusToDelete(id);

        model.addAttribute("title", "Edit Bus");
        model.addAttribute("view", "buses/delete");
        model.addAttribute("deleteBusModel", bus);

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteBus(@PathVariable("id") Long id) {

        this.busService.deleteBus(id);
        return "redirect:/buses";
    }

    @ExceptionHandler(BusNotFoundException.class)
    public String busNotFound() {

        return "error/404";
    }
}
