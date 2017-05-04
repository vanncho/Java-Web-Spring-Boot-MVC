package com.onlinebusreservation.areas.booking.repositories;

import com.onlinebusreservation.areas.booking.entities.Booking;
import com.onlinebusreservation.areas.bus.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "SELECT b FROM Booking AS b WHERE b.user.id = :userId")
    List<Booking> getAllByUser(@Param("userId") Long userId);
}
