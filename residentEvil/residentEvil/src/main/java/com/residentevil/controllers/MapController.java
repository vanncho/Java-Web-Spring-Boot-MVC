package com.residentevil.controllers;

import com.residentevil.services.VirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MapController {

    @Autowired
    private VirusService virusService;

    @GetMapping("/map")
    public String getMap(Model model) {

        String geoJson = this.virusService.getGeoData();
        model.addAttribute("geoJson", geoJson);
        return "map/map";
    }
}
