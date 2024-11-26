package com.telecom.telecom.repositories;

import com.telecom.telecom.entities.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Integer> {

        @Query("SELECT c FROM CustomerAccount c WHERE TRIM(c.mobileNo) = TRIM(:mobileNo)")
        Optional<CustomerAccount> findCustomerAccountsByMobileNo(@Param("mobileNo") String mobileNo);
}
