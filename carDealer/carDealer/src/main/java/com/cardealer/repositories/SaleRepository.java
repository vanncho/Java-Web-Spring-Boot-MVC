package com.cardealer.repositories;

import com.cardealer.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT s FROM Sale AS s")
    List<Sale> getAll();

    @Query(value = "SELECT s FROM Sale AS s WHERE s.discount > 0")
    List<Sale> getAllWithDiscount();

    @Query(value = "SELECT s FROM Sale AS s WHERE s.discount = :discount")
    List<Sale> getAllSalesByDiscount(@Param("discount") Double discount);

    @Query(value = "SELECT s FROM Sale AS s WHERE id = :id")
    Sale getSaleById(@Param("id") Long id);
}
