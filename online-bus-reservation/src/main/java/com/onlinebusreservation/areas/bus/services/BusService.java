package com.onlinebusreservation.areas.bus.services;

import com.onlinebusreservation.areas.bus.models.binding.AddBusModel;
import com.onlinebusreservation.areas.bus.models.view.*;

import java.util.List;

public interface BusService {

    List<BusListModel> getAllBuses();

    void addNewBus(AddBusModel addBusModel);

    AddBusModel findBus(Long id);

    void updateBus(AddBusModel addBusModel, Long id);

    BusDeleteViewModel findBusToDelete(Long id);

    void deleteBus(Long id);

    List<BusSearchModel> findByOriginAndDestination(String origin, String destination, String dateOfJourney);

    BusBookViewModel getBookBus(Long id, String dateOfJourney);

    void addReservedSeat(Long id, Integer[] seatNumber);

    BusBookSelectDateViewModel getSelectedBus(Long id);

    BusBookPreViewModel getPreviewBus(Long busId);
}
