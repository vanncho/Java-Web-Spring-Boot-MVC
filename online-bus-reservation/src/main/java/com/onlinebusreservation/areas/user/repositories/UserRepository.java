package com.onlinebusreservation.areas.user.repositories;
import com.onlinebusreservation.areas.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    T findOneByUsername(String username);

    @Query(value = "SELECT * FROM users AS u\n" +
                   " INNER JOIN users_roles AS ur\n" +
                   "\tON ur.user_id = u.id\n" +
                   " INNER JOIN roles AS r\n" +
                   "\tON r.id = ur.role_id\n" +
                   " WHERE r.authority = :role", nativeQuery = true)
    List<T> getAllByRoleAdmin(@Param("role") String role);

    @Query(value = "SELECT * FROM users AS u\n" +
                   " INNER JOIN users_roles AS ur\n" +
                   "\tON ur.user_id = u.id\n" +
                   " INNER JOIN roles AS r\n" +
                   "\tON r.id = ur.role_id\n" +
                   " WHERE r.authority = :role", nativeQuery = true)
    List<T> getAllByRoleUser(@Param("role") String role);

    @Query(value = "UPDATE User SET password = :password WHERE id = :id")
    @Transactional
    @Modifying
    void changePassword(@Param("id") Long id,
                        @Param("password") String newPassword);

    User findOneByEmail(String email);
}
