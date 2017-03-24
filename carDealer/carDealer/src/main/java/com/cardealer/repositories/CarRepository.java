package com.cardealer.repositories;

import com.cardealer.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "SELECT c FROM Car AS c WHERE c.make = :make ORDER BY c.model ASC, c.travelledDistance DESC")
    List<Car> getAllCarsByMake(@Param(value = "make") String make);

    @Query(value = "SELECT c FROM Car AS c ORDER BY c.make, c.model")
    List<Car> getAllCars();

    @Query(value = "SELECT DISTINCT c.make FROM Car AS c ORDER BY c.make")
    List<String> getAllCarsByMake();

    Car getById(Long id);
}
