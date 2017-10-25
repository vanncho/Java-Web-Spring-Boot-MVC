package com.onlinebusreservation.controllers;

import com.onlinebusreservation.areas.bus.models.view.BusListModel;
import com.onlinebusreservation.areas.bus.models.view.BusSearchModel;
import com.onlinebusreservation.areas.bus.services.BusService;
import com.onlinebusreservation.areas.city.models.view.CityViewModel;
import com.onlinebusreservation.areas.city.services.CityService;
import com.onlinebusreservation.areas.user.services.UserFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    private final BusService busService;

    private final CityService cityService;

    private final UserFinder userFinder;

    @Autowired
    public HomeController(BusService busService, CityService cityService, UserFinder userFinder) {

        this.busService = busService;
        this.cityService = cityService;
        this.userFinder = userFinder;
    }

    @GetMapping("/")
    public String getHome(Model model,
                          @RequestParam(required = false) String origin,
                          @RequestParam(required = false) String destination,
                          @RequestParam(required = false) String dateOfJourney,
                          Authentication authentication,
                          HttpSession session) {

        if (null != authentication) {

            String role = this.userFinder.userRole(authentication);

            if (role.equals("ROLE_ADMIN")) {
                return "home-admin";
            }
        }

        List<BusListModel> buses = this.busService.getAllBuses();
        List<CityViewModel> cities = this.cityService.getAllCities();
        List<BusSearchModel> searched = null;
        
        if (null != origin && null != destination && null != dateOfJourney) {
        	
        	if (!origin.equals("") && !destination.equals("") && !dateOfJourney.equals("")) {

            	searched = this.busService.findByOriginAndDestination(origin, destination, dateOfJourney);
            	
            	if (searched.size() > 0) {

                    model.addAttribute("searchedBuses", searched);
                    session.setAttribute("chosenDate", dateOfJourney);
                    
                } else {

                    model.addAttribute("nobus", "No Result Found");
                }
            }
        }
        
        model.addAttribute("title", "Online Bus Reservation");
        model.addAttribute("buses", buses);
        model.addAttribute("cities", cities);
        model.addAttribute("view", "home");
        return "base-layout";
    }
}
