package com.telecom.telecom.repositories;

import com.telecom.telecom.entities.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Integer> {

    Optional<CustomerProfile> findByNationalID(Integer nationalId);
}
