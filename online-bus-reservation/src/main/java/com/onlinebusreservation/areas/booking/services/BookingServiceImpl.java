package com.onlinebusreservation.areas.booking.services;

import com.onlinebusreservation.areas.booking.entities.Booking;
import com.onlinebusreservation.areas.booking.exceptions.BookingNotFoundException;
import com.onlinebusreservation.areas.booking.models.binding.BookingRegistrationModel;
import com.onlinebusreservation.areas.booking.models.view.BookingCancellationViewModel;
import com.onlinebusreservation.areas.booking.models.view.BookingsByUserViewModel;
import com.onlinebusreservation.areas.booking.models.view.BookingsPrintViewModel;
import com.onlinebusreservation.areas.booking.repositories.BookingRepository;
import com.onlinebusreservation.areas.bus.entities.Bus;
import com.onlinebusreservation.areas.bus.models.view.BusBookingDeleteModel;
import com.onlinebusreservation.areas.bus.repositories.BusRepository;
import com.onlinebusreservation.areas.seat.entities.Seat;
import com.onlinebusreservation.areas.seat.repositories.SeatRepository;
import com.onlinebusreservation.areas.user.entities.User;
import com.onlinebusreservation.areas.user.model.view.UserBookingCancelModel;
import com.onlinebusreservation.areas.user.repositories.AbstractUserRepository;
import com.onlinebusreservation.mappers.ModelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onlinebusreservation.constants.Constants;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final BusRepository busRepository;

    private final SeatRepository seatRepository;

    private final AbstractUserRepository userRepository;

    private final ModelParser modelParser;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,
                              BusRepository busRepository,
                              SeatRepository seatRepository,
                              AbstractUserRepository userRepository,
                              ModelParser modelParser) {

        this.bookingRepository = bookingRepository;
        this.busRepository = busRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.modelParser = modelParser;
    }

    @Override
    @Transactional
    public void create(BookingRegistrationModel bookingRegistrationModel) {

        Booking booking = new Booking();
        Bus bus = this.busRepository.findOne(bookingRegistrationModel.getBusId());
        User user = this.userRepository.findOne(bookingRegistrationModel.getUserId());

        int[] selectedSeats = bookingRegistrationModel.getSelectedSeat();

        for (int selectedSeat : selectedSeats) {

            Seat seat = this.seatRepository.findOne(Long.valueOf(selectedSeat));
            booking.getSeats().add(seat);
            bus.getSeats().add(seat);
        }

        booking.setBus(bus);
        booking.setUser(user);
        booking.setTotalCost(bookingRegistrationModel.getTotalCost());

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date journeyDate = null;

        try {
            journeyDate = sdf.parse(bookingRegistrationModel.getDateOfJourney());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date now = new Date();
        booking.setDateOfJourney(journeyDate);
        booking.setBookingDate(now);

        this.bookingRepository.save(booking);
    }

    @Override
    public List<BookingsByUserViewModel> getBookingsByUser(long userId) {

        List<Booking> bookings = this.bookingRepository.getAllByUser(userId);
        List<BookingsByUserViewModel> bookingModels = new ArrayList<>();

        for (Booking booking : bookings) {

            BookingsByUserViewModel currentViewModel = new BookingsByUserViewModel();
            currentViewModel.setId(booking.getId());

            Bus bus = booking.getBus();
            BusBookingDeleteModel busBookingDeleteModel = this.modelParser.convert(bus, BusBookingDeleteModel.class);
            currentViewModel.setBus(busBookingDeleteModel);

            DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
            String date = df.format(booking.getDateOfJourney());
            currentViewModel.getBus().setDateOfJourney(date);
            currentViewModel.setTotalCost(booking.getTotalCost());
            currentViewModel.setSeatsCount(booking.getSeats().size());

            User user = this.userRepository.findOne(userId);
            UserBookingCancelModel userBookingCancelModel = this.modelParser.convert(user, UserBookingCancelModel.class);
            currentViewModel.setUser(userBookingCancelModel);

            bookingModels.add(currentViewModel);
        }

        return bookingModels;
    }

    @Override
    public BookingCancellationViewModel getBookingById(Long id) {

        Booking booking = this.bookingRepository.findOne(id);

        if (booking == null) {

            throw new BookingNotFoundException();
        }

        BookingCancellationViewModel bookingCancellationViewModel = this.modelParser.convert(booking, BookingCancellationViewModel.class);

        int[] seats = new int[booking.getSeats().size()];

        int count = 0;
        for (Seat seat : booking.getSeats()) {

            seats[count] = seat.getSeatNumber();
            count++;
        }

        bookingCancellationViewModel.setSelectedSeat(seats);

        DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
        String date = df.format(booking.getDateOfJourney());
        bookingCancellationViewModel.setDateOfJourney(date);

        bookingCancellationViewModel.setTicketPrice(booking.getTotalCost());

        return bookingCancellationViewModel;
    }

    @Override
    @Transactional
    public void cancelBooking(Long id, BookingCancellationViewModel bookingCancellationViewModel) {

        Bus bus = this.busRepository.findOne(bookingCancellationViewModel.getBus().getId());
        User user = this.userRepository.findOne(bookingCancellationViewModel.getUser().getId());

        int[] seats = bookingCancellationViewModel.getSelectedSeat();
        for (int seat : seats) {

            Seat currSeat = this.seatRepository.findOne((long) seat);
            bus.getSeats().remove(currSeat);
        }

        Booking booking = this.bookingRepository.findOne(id);
        bus.getBookings().remove(booking);
        user.getBookings().remove(booking);

        this.bookingRepository.delete(booking);
    }

    @Override
    @Transactional
    public void deleteBooking(Long bookingId, BookingsByUserViewModel bookingsByUserViewModel) {

        Booking booking = this.bookingRepository.findOne(bookingId);

        if (booking == null) {

            throw new BookingNotFoundException();
        }

        Bus bus = this.busRepository.findOne(bookingsByUserViewModel.getBus().getId());
        User user = this.userRepository.findOne(bookingsByUserViewModel.getUser().getId());

        Set<Seat> seats = booking.getSeats();

        for (Seat seat : seats) {

            bus.getSeats().remove(seat);
        }

        bus.getBookings().remove(booking);
        user.getBookings().remove(booking);

        this.bookingRepository.delete(booking);
    }

    @Override
    public BookingsPrintViewModel getPrintBooking(Long id) {

        Booking booking = this.bookingRepository.findOne(id);

        if (booking == null) {

            throw new BookingNotFoundException();
        }

        Bus bus = booking.getBus();
        User user = booking.getUser();

        BookingsPrintViewModel bookingsPrintViewModel = new BookingsPrintViewModel();
        bookingsPrintViewModel.setBusBusName(bus.getBusName());
        bookingsPrintViewModel.setUserFullName(user.getFullName());
        bookingsPrintViewModel.setDateOfJourney(booking.getDateOfJourney().toString().substring(0, 10));
        bookingsPrintViewModel.setDestinationTo(bus.getDestinationTo().getName());
        bookingsPrintViewModel.setOriginatedFrom(bus.getOriginatedFrom().getName());
        bookingsPrintViewModel.setTotalCost(booking.getTotalCost());
        bookingsPrintViewModel.setTimeFromOrigin(bus.getTimeFromOrigin());

        int[] seats = new int[booking.getSeats().size()];

        int count = 0;
        for (Seat seat : booking.getSeats()) {

            seats[count] = seat.getSeatNumber();
            count++;
        }

        bookingsPrintViewModel.setSelectedSeat(seats);

        return bookingsPrintViewModel;
    }
}
