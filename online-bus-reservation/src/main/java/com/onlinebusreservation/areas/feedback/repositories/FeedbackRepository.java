package com.onlinebusreservation.areas.feedback.repositories;

import com.onlinebusreservation.areas.feedback.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value = "SELECT f FROM Feedback AS f ORDER BY f.date DESC")
    List<Feedback> findAll();

    @Query(value = "UPDATE Feedback SET isAnswered = :isAnswered WHERE id = :feedbackId")
    @Transactional
    @Modifying
    void updateIsAnswered(@Param("feedbackId") Long feedbackId,
                          @Param("isAnswered") boolean isAnswered);
}
