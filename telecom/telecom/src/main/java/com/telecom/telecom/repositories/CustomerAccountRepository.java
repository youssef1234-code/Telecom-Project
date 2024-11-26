package com.telecom.telecom.repositories;

import com.telecom.telecom.entities.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Integer> {

    @Query(value = "SELECT * FROM customer_account ca WHERE ca.mobileNo = :customerMobileNo ",nativeQuery = true)
    Optional<CustomerAccount> findByMobileNo(@Param("customerMobileNo") String customerMobileNo);
}
