package com.onlinebusreservation.areas.info.repositories;

import com.onlinebusreservation.areas.info.entities.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long> {

    @Query(value = "SELECT ci FROM CompanyInfo AS ci")
    CompanyInfo getCompanyInfo();

    @Query(value = "UPDATE CompanyInfo SET companyName = :companyName, address = :address, town = :town, phoneNumber = :phoneNumber WHERE id = :id")
    @Transactional
    @Modifying
    void updateInfo(@Param("id") Long id,
                    @Param("companyName") String companyName,
                    @Param("address") String address,
                    @Param("town") String town,
                    @Param("phoneNumber") String phoneNumber);
}
