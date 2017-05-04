package com.residentevil.repositories;

import com.residentevil.entities.Virus;
import com.residentevil.entities.enumerations.Magnitude;
import com.residentevil.entities.enumerations.Mutation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface VirusRepository extends JpaRepository<Virus, Long> {


    @Query(value = "UPDATE Virus SET name = :name, " +
                   "description = :description, " +
                   "creator = :creator, " +
                   "sideEffects = :sideEffects, " +
                   "isDeadly = :isDeadly, " +
                   "isCurable = :isCurable, " +
                   "mutation = :mutation, " +
                   "turnoverRate = :turnoverRate, " +
                   "hoursUntilTurn = :hoursUntilTurn, " +
                   "magnitude = :magnitude, " +
                   "releasedOn = :releasedOn " +
                   "WHERE id = :id")
    @Transactional
    @Modifying
    void updateVirus(@Param("id") Long id,
                     @Param("name") String name,
                     @Param("description") String description,
                     @Param("creator") String creator,
                     @Param("sideEffects") String sideEffects,
                     @Param("isDeadly") boolean isDeadly,
                     @Param("isCurable") boolean isCurable,
                     @Param("mutation") Mutation mutation,
                     @Param("turnoverRate") Double turnoverRate,
                     @Param("hoursUntilTurn") Integer hoursUntilTurn,
                     @Param("magnitude") Magnitude magnitude,
                     @Param("releasedOn") Date releasedOn);
}
