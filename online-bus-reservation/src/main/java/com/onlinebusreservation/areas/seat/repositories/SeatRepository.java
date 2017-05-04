package com.onlinebusreservation.areas.seat.repositories;

import com.onlinebusreservation.areas.seat.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Seat findSeatBySeatNumber(Integer seatNumber);
}
