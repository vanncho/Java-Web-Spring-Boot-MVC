package com.cardealer.repositories;

import com.cardealer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User AS u WHERE u.role = 'ADMIN'")
    User hasAdmin();

    @Query(value = "SELECT u FROM User AS u WHERE u.email = :email")
    User exists(@Param("email") String email);

    @Query(value = "SELECT u FROM User AS u WHERE u.email = :email AND u.password = :password")
    User getByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}
