package com.telecom.telecom.controllers;

import com.telecom.telecom.dtos.CustomerAccountDto;
import com.telecom.telecom.entities.CustomerAccount;
import com.telecom.telecom.mappers.DtoEntityMapper;
import com.telecom.telecom.repositories.CustomerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/api/customer")
public class CustomerAccountController {

    @Autowired
    CustomerAccountRepository customerAccountRepository;

    @Autowired
    DtoEntityMapper dtoEntityMapper;

    @GetMapping("/{mobile}")
    public CustomerAccountDto getCustomerByMobile(@PathVariable String mobile) {
        Optional<CustomerAccount> customerAccount = customerAccountRepository.findByMobileNo(mobile);
        if (customerAccount.isPresent()) {
            return dtoEntityMapper.toCustomerAccountDto(customerAccount.get());
        }
        return null;
    }
}
