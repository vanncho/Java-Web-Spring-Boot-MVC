package com.onlinebusreservation.areas.bus.repositories;

import com.onlinebusreservation.areas.bus.entities.Bus;
import com.onlinebusreservation.areas.city.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    @Query(value = "UPDATE Bus SET busName = :busName, " +
                                  "originatedFrom = :origin, " +
                                  "destinationTo = :destination, " +
                                  "timeFromOrigin = :time, " +
                                  "numberOfSeats = :seats, " +
                                  "ticketPrice = :ticketPrice " +
                                  "WHERE id = :id")
    @Transactional
    @Modifying
    void updateBus(@Param("id") Long id,
                   @Param("busName") String busName,
                   @Param("origin") City origin,
                   @Param("destination")City destination,
                   @Param("time") String time,
                   @Param("seats") Integer seats,
                   @Param("ticketPrice") Double ticketPrice);

    @Query(value = "SELECT b FROM Bus AS b WHERE b.originatedFrom = :origin AND b.destinationTo = :destination")
    List<Bus> findAllByOriginAndDestination(@Param("origin") City origin, @Param("destination") City destination);
}
