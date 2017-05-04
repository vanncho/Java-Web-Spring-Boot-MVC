package com.onlinebusreservation.areas.user.repositories;

import com.onlinebusreservation.areas.user.entities.BasicUser;
import com.onlinebusreservation.areas.user.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstractUserRepository extends UserRepository<User> {

    BasicUser findByUsername(String userName);

    @Query(value = "SELECT COUNT(m) FROM User AS u INNER JOIN u.messages AS m WHERE u.id = :id AND m.isRead = false")
    int getUnreadMessages(@Param("id") Long id);
}
