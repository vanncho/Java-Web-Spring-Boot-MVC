package com.cardealer.repositories;

import com.cardealer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    List<Customer> getAllByOrderByBirthDateAscIsYoungDriverAsc();

    List<Customer> getAllByOrderByBirthDateDescIsYoungDriverAsc();

    @Query(value = "SELECT c FROM Customer AS c ORDER BY CASE WHEN c.isYoungDriver = TRUE THEN c.birthDate END DESC")
    List<Customer> findAllByOrderByBirthDateDesc();

    @Query(value = "SELECT cu.name, COUNT(s.id), SUM(p.price) FROM customers AS cu\n" +
                  " INNER JOIN sales AS s\n" +
                  " \tON s.customer_id = cu.id\n" +
                  " INNER JOIN cars AS car\n" +
                  " \tON car.id = s.car_id\n" +
                  " INNER JOIN parts_cars AS cp\n" +
                  " \tON cp.car_id = car.id\n" +
                  " INNER JOIN parts AS p\n" +
                  " \tON p.id = cp.part_id\n" +
                  " WHERE cu.id = :id\n" +
                  " GROUP BY cu.name;", nativeQuery = true)
    List<Object[]> getTotalSalesByCustomer(@Param(value = "id") Long id);

    @Query(value = "SELECT c FROM Customer AS c WHERE c.name = :name")
    Customer getCustomerByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Customer SET name = :name, birthDate = :birthDate, isYoungDriver = :isYoungDriver WHERE id = :id")
    void edit(@Param("id") Long id,
              @Param("name") String name,
              @Param("birthDate") Date birthDate,
              @Param("isYoungDriver") boolean isYoungDriver);
}
