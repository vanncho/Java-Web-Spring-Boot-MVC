package com.onlinebusreservation.areas.bus.services;

import com.onlinebusreservation.areas.booking.entities.Booking;
import com.onlinebusreservation.areas.bus.entities.Bus;
import com.onlinebusreservation.areas.bus.exceptions.BusNotFoundException;
import com.onlinebusreservation.areas.bus.models.view.*;
import com.onlinebusreservation.areas.city.entities.City;
import com.onlinebusreservation.areas.seat.entities.Seat;
import com.onlinebusreservation.areas.seat.repositories.SeatRepository;
import com.onlinebusreservation.constants.Constants;
import com.onlinebusreservation.mappers.ModelParser;
import com.onlinebusreservation.areas.bus.models.binding.AddBusModel;
import com.onlinebusreservation.areas.bus.repositories.BusRepository;
import com.onlinebusreservation.areas.city.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    private final CityRepository cityRepository;

    private final SeatRepository seatRepository;

    private final ModelParser modelParser;

    @Autowired
    public BusServiceImpl(BusRepository busRepository,
                          CityRepository cityRepository,
                          SeatRepository seatRepository,
                          ModelParser modelParser) {

        this.busRepository = busRepository;
        this.cityRepository = cityRepository;
        this.seatRepository = seatRepository;
        this.modelParser = modelParser;
    }

    @Override
    public List<BusListModel> getAllBuses() {

        List<Bus> buses = this.busRepository.findAll();
        List<BusListModel> busListModels = new ArrayList<>();

        for (Bus bus : buses) {

            BusListModel busListModel = this.modelParser.convert(bus, BusListModel.class);
            busListModels.add(busListModel);
        }

        return busListModels;
    }

    @Override
    public void addNewBus(AddBusModel addBusModel) {

        Bus bus = this.modelParser.convert(addBusModel, Bus.class);

        City origin = this.cityRepository.findOneByName(addBusModel.getOriginatedFromName());
        City destination = this.cityRepository.findOneByName(addBusModel.getDestinationToName());
        String time = addBusModel.getHour() + ":" + addBusModel.getMinutes();

        bus.setOriginatedFrom(origin);
        bus.setDestinationTo(destination);
        bus.setTimeFromOrigin(time);

        this.busRepository.save(bus);
    }

    @Override
    public AddBusModel findBus(Long id) {

        Bus bus = this.busRepository.findOne(id);

        if (bus == null) {

            throw new BusNotFoundException();
        }

        AddBusModel addBusModel = this.modelParser.convert(bus, AddBusModel.class);

        String[] timeTokens = bus.getTimeFromOrigin().split(":");
        Integer hour = Integer.valueOf(timeTokens[0]);
        Integer minutes = Integer.valueOf(timeTokens[1]);

        addBusModel.setHour(hour);
        addBusModel.setMinutes(minutes);

        return addBusModel;
    }

    @Override
    public void updateBus(AddBusModel addBusModel, Long id) {

        String busName = addBusModel.getBusName();
        City origin = this.cityRepository.findOneByName(addBusModel.getOriginatedFromName());
        City destination = this.cityRepository.findOneByName(addBusModel.getDestinationToName());
        Integer hour = addBusModel.getHour();
        Integer minutes = addBusModel.getMinutes();
        String time = hour + ":" + minutes;
        Integer seats = addBusModel.getNumberOfSeats();
        Double ticketPrice = addBusModel.getTicketPrice();

        this.busRepository.updateBus(id, busName, origin, destination, time, seats, ticketPrice);
    }

    @Override
    public BusDeleteViewModel findBusToDelete(Long id) {

        Bus bus = this.busRepository.findOne(id);

        if (bus == null) {

            throw new BusNotFoundException();
        }

        BusDeleteViewModel busDeleteViewModel = this.modelParser.convert(bus, BusDeleteViewModel.class);
        return busDeleteViewModel;
    }

    @Override
    public void deleteBus(Long id) {

        this.busRepository.delete(id);
    }

    @Override
    public List<BusSearchModel> findByOriginAndDestination(String origin, String destination, String dateOfJourney) {

        if (null == dateOfJourney || dateOfJourney.equals("")) {

            return new ArrayList<BusSearchModel>();
        }

        Date[] journeyAndDateNow = this.getJourneyAndDateNow(dateOfJourney);

        if (journeyAndDateNow[0].before(journeyAndDateNow[1]) || journeyAndDateNow[0].equals(journeyAndDateNow[1])) {

            return new ArrayList<BusSearchModel>();
        }

        City originCity = this.cityRepository.findOneByName(origin);
        City destinationCity = this.cityRepository.findOneByName(destination);

        List<Bus> buses = this.busRepository.findAllByOriginAndDestination(originCity, destinationCity);
        List<BusSearchModel> busListModels = new ArrayList<>();

        for (Bus bus : buses) {

            BusSearchModel busListModel = this.modelParser.convert(bus, BusSearchModel.class);

            busListModel.setFreeSeats(bus.getNumberOfSeats() - bus.getSeats().size());
            busListModels.add(busListModel);
        }

        return busListModels;
    }

    @Override
    public BusBookViewModel getBookBus(Long busId, String dateOfJourney) {

        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = null;

        try {
            date = format.parse(dateOfJourney);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        Bus bus = this.busRepository.findOne(busId);

        if (bus == null) {

            throw new BusNotFoundException();
        }

        BusBookViewModel busBookViewModel = new BusBookViewModel();
        busBookViewModel.setBusName(bus.getBusName());
        busBookViewModel.setDestinationToName(bus.getDestinationTo().getName());
        busBookViewModel.setOriginatedFromName(bus.getOriginatedFrom().getName());
        busBookViewModel.setTicketPrice(bus.getTicketPrice());
        busBookViewModel.setTimeFromOrigin(bus.getTimeFromOrigin());


        for (Seat seat : bus.getSeats()) {

            for (Booking booking : seat.getBookings()) {

                if (booking.getDateOfJourney().compareTo(date) == 0) {

                    busBookViewModel.getSeats().add(seat.getSeatNumber());
                }
            }
        }

        return busBookViewModel;
    }

    @Override
    public void addReservedSeat(Long id, Integer[] seatNumber) {

        for (Integer number : seatNumber) {

            Seat seat = this.seatRepository.findSeatBySeatNumber(number);
            Bus bus = this.busRepository.findOne(id);
            bus.getSeats().add(seat);
        }

    }

    @Override
    public BusBookSelectDateViewModel getSelectedBus(Long busId) {

        Bus bus = this.busRepository.findOne(busId);
        BusBookSelectDateViewModel busBookSelectDateViewModel = this.modelParser.convert(bus, BusBookSelectDateViewModel.class);
        return busBookSelectDateViewModel;
    }

    @Override
    public BusBookPreViewModel getPreviewBus(Long busId) {

        Bus bus = this.busRepository.findOne(busId);
        BusBookPreViewModel busBookPreViewModel = this.modelParser.convert(bus, BusBookPreViewModel.class);
        return busBookPreViewModel;
    }

    private Date[] getJourneyAndDateNow(String dateOfJourney) {

        LocalDate localDate = LocalDate.now();
        String today = String.valueOf(localDate);
        Date now = null;

        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date journey = null;

        try {
            journey = format.parse(dateOfJourney);
            now = format.parse(today);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        Date[] dates = new Date[2];
        dates[0] = journey;
        dates[1] = now;

        return dates;
    }
}
