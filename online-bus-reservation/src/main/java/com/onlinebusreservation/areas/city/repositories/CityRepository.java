package com.onlinebusreservation.areas.city.repositories;

import com.onlinebusreservation.areas.city.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findOneByName(String name);
}
