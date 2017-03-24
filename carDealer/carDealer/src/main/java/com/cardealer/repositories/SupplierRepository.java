package com.cardealer.repositories;

import com.cardealer.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query(value = "SELECT s FROM Supplier AS s WHERE s.isImporter = FALSE")
    List<Supplier> getAllLocalSuppliers();

    @Query(value = "SELECT s FROM Supplier AS s WHERE s.isImporter = TRUE")
    List<Supplier> getAllImportersSuppliers();

    @Query(value = "SELECT s FROM Supplier AS s WHERE s.name = :supplierName")
    Supplier findByName(@Param("supplierName") String supplierName);
}
