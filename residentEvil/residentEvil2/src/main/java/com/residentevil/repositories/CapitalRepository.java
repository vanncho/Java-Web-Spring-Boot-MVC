package com.residentevil.repositories;

import com.residentevil.entities.Capital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, Long> {

    @Query(value = "SELECT DISTINCT c FROM Capital AS c WHERE c.name = :name")
    Capital findByName(@Param("name") String name);
}
