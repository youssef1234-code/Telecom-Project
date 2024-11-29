package com.telecom.telecom.controllers;

import com.telecom.telecom.dtos.CustomerAccountDto;
import com.telecom.telecom.entities.CustomerAccount;
import com.telecom.telecom.entities.CustomerProfile;
import com.telecom.telecom.mappers.DtoEntityMapper;
import com.telecom.telecom.repositories.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/api/customer")
public class CustomerAccountController {

    @Autowired
    CustomerAccountRepository customerAccountRepository;

    @Autowired
    CustomerProfileRepository customerProfileRepository;

    @Autowired
    DtoEntityMapper dtoEntityMapper;
    @Autowired
    private ViewsRepository viewsRepository;
    @Autowired
    private FunctionsRepository functionsRepository;
    @Autowired
    private ProceduresRepository proceduresRepository;

    @GetMapping("/servicePlans")
    public ResponseEntity<?> getAllServicePlans(){return ResponseEntity.ok(viewsRepository.getServicePlans());}

    @GetMapping("/consumption/{planId}/{startDate}/{endDate}")
    public ResponseEntity<?> getPlanConsumption(@PathVariable String planId, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate)
    {return ResponseEntity.ok(functionsRepository.getConsumption(planId, startDate, endDate));}

    @Transactional
    @GetMapping("/unsubscribed/{mobileNo}")
    public ResponseEntity<?> getUnsubscribedPlans(@PathVariable String mobileNo)
    {return ResponseEntity.ok(proceduresRepository.unsubscribedPlans(mobileNo));}

    @Transactional
    @GetMapping("/monthUsage/{mobileNo}")
    public ResponseEntity<?> getMonthUsage(@PathVariable String mobileNo)
    {return ResponseEntity.ok(functionsRepository.getUsagePlanCurrentMonth(mobileNo));}

    @Transactional
    @GetMapping("/cashbacks/{nId}")
    public ResponseEntity<?> getCashbacks(@PathVariable Integer nId)
    {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("National ID", nId);
        returnMap.put("Cashback Amount", functionsRepository.getCashbackWalletCustomer(nId));
        return ResponseEntity.ok(returnMap);

    }
}
