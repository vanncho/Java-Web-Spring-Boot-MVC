package com.onlinebusreservation.areas.bus.controllers;

import com.onlinebusreservation.areas.bus.models.view.BusListModel;
import com.onlinebusreservation.areas.bus.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/routes")
public class RoutesController {

    private BusService busService;

    @Autowired
    public RoutesController(BusService busService) {

        this.busService = busService;
    }

    @GetMapping("")
    public String getBuses(Model model) {

        List<BusListModel> buses = this.busService.getAllBuses();

        model.addAttribute("title", "Bus Management");
        model.addAttribute("buses", buses);
        model.addAttribute("view", "routes/routes");
        return "base-layout";
    }
}
