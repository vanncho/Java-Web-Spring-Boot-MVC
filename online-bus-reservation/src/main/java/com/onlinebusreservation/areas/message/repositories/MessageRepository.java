package com.onlinebusreservation.areas.message.repositories;

import com.onlinebusreservation.areas.message.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT m FROM Message AS m WHERE m.user.id = :id")
    List<Message> getUserMessages(@Param("id") Long id);
}
