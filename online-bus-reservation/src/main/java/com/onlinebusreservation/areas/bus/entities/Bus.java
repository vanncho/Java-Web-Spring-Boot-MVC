package com.onlinebusreservation.areas.bus.entities;

import com.onlinebusreservation.areas.booking.entities.Booking;
import com.onlinebusreservation.areas.city.entities.City;
import com.onlinebusreservation.areas.seat.entities.Seat;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "buses")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bus_name")
    private String busName;

    @OneToOne
    @JoinColumn(name = "origin_city_id", referencedColumnName = "id")
    private City originatedFrom;

    @OneToOne
    @JoinColumn(name = "destination_city_id", referencedColumnName = "id")
    private City destinationTo;

    @Column(name = "time_from_origin")
    private String timeFromOrigin;

    @Column(name = "nuber_of_seats")
    private Integer numberOfSeats;

    @Column(name = "ticket_price")
    private Double ticketPrice;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "buses_seats",
    joinColumns = @JoinColumn(name = "bus_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "seat_id", referencedColumnName = "id"))
    private Set<Seat> seats;

    @OneToMany(mappedBy = "bus", targetEntity = Booking.class)
    private Set<Booking> bookings;

    public Bus() {

        this.seats = new HashSet<>();
        this.bookings = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public City getOriginatedFrom() {
        return originatedFrom;
    }

    public void setOriginatedFrom(City originatedFrom) {
        this.originatedFrom = originatedFrom;
    }

    public City getDestinationTo() {
        return destinationTo;
    }

    public void setDestinationTo(City destinationTo) {
        this.destinationTo = destinationTo;
    }

    public String getTimeFromOrigin() {
        return timeFromOrigin;
    }

    public void setTimeFromOrigin(String timeFromOrigin) {
        this.timeFromOrigin = timeFromOrigin;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }
}
