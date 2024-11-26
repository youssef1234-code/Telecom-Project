package com.telecom.telecom.controllers;

import com.telecom.telecom.dtos.CustomerAccountDto;
import com.telecom.telecom.entities.CustomerAccount;
import com.telecom.telecom.entities.CustomerProfile;
import com.telecom.telecom.mappers.DtoEntityMapper;
import com.telecom.telecom.repositories.CustomerAccountRepository;
import com.telecom.telecom.repositories.CustomerProfileRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/customer")
public class CustomerAccountController {

    @Autowired
    CustomerAccountRepository customerAccountRepository;

    @Autowired
    CustomerProfileRepository customerProfileRepository;

    @Autowired
    DtoEntityMapper dtoEntityMapper;

    @GetMapping("/{mobile}")
    public CustomerAccountDto getCustomerByMobile(@PathVariable String mobile) {
        Optional<CustomerAccount> customerAccount = customerAccountRepository.findCustomerAccountsByMobileNo(mobile);
        return customerAccount.map(account -> dtoEntityMapper.toCustomerAccountDto(account)).orElse(null);
    }

    @GetMapping("/nId/{nationalId}")
    public Map<Integer,String> getCustomerByNationalId(@PathVariable Integer nationalId) {
        Optional<CustomerProfile> customerProfile = customerProfileRepository.findByNationalID(nationalId);
        return customerProfile.map(account -> Map.of(customerProfile.get().getNationalID(),customerProfile.get().getEmail())).orElse(null);
    }

}
