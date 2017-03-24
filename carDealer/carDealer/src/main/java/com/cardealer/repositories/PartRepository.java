package com.cardealer.repositories;

import com.cardealer.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

    @Query(value = "SELECT p FROM Car AS c JOIN c.parts AS p WHERE c.id = :id")
    List<Part> getAllCarParts(@Param(value = "id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Part SET price = :price, quantity = :quantity WHERE id = :id")
    void edit(@Param("id") Long id,
              @Param("price") Double price,
              @Param("quantity") Long quantity);

    @Query(value = "SELECT p FROM Part AS p WHERE p.name = :name")
    Part getPartByName(@Param(value = "name") String name);
}
