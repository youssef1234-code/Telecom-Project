package com.telecom.telecom.controllers;

import com.telecom.telecom.dtos.CustomerAccountDto;
import com.telecom.telecom.entities.CustomerAccount;
import com.telecom.telecom.entities.CustomerProfile;
import com.telecom.telecom.mappers.DtoEntityMapper;
import com.telecom.telecom.repositories.*;
import com.telecom.telecom.utils.HelperUtils;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Transactional
    @PostMapping("/consumption")
    public ResponseEntity<?> getPlanConsumption(@RequestBody Map<String, String> requestParams)
    {

        if(requestParams.isEmpty() || !requestParams.containsKey("startDate") || !requestParams.containsKey("planName") || !requestParams.containsKey("endDate")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }
        
        String planName = requestParams.get("planName");
        String startDate = requestParams.get("startDate");
        String endDate = requestParams.get("endDate");

        
        if(Strings.isBlank(planName) || Strings.isBlank(startDate) || Strings.isBlank(endDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Plan Name, Start Date and End Date cannot be empty!"));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formattedstartDate = HelperUtils.toDateFormat(startDate, formatter);
        LocalDate formattedendDate = HelperUtils.toDateFormat(endDate, formatter);

        if(Objects.isNull(formattedstartDate)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid Start Date Format!"));
        }

        if(Objects.isNull(formattedendDate)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid End Date Format!"));
        }

        return ResponseEntity.ok(functionsRepository.getConsumption(planName, formattedstartDate, formattedendDate));
        
    }
    
    @Transactional
    @PostMapping("/unsubscribed")
    public ResponseEntity<?> getUnsubscribedPlans(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        return ResponseEntity.ok(proceduresRepository.unsubscribedPlans(mobileNum));
    }

    @Transactional
    @PostMapping("/monthUsage")
    public ResponseEntity<?> getMonthUsage(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        return ResponseEntity.ok(functionsRepository.getUsagePlanCurrentMonth(mobileNum));
    }

    @Transactional
    @PostMapping("/cashbacks")
    public ResponseEntity<?> getCashbacks(@RequestBody Map<String, String> requestParams)
    {

        if(requestParams.isEmpty() || !requestParams.containsKey("nId") || !requestParams.containsKey("mobileNum"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String nId = requestParams.get("nId");

        Integer nIdInteger = HelperUtils.toInteger(nId);
        if(Objects.isNull(nIdInteger)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid National Id Format!"));
        }

        return ResponseEntity.ok(functionsRepository.getCashbackWalletCustomer(nIdInteger));

    }

    @Transactional
    @GetMapping("/all-benefits")
    public ResponseEntity<?> getAllBenefits() {
        return ResponseEntity.ok(viewsRepository.getAllBenefits());
    }


    @Transactional
    @PostMapping("/unresolved")
    public ResponseEntity<?> getUnresolvedTickets(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("nId") || !requestParams.containsKey("planName") )
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String nId = requestParams.get("nId");

        Integer nIdInteger = HelperUtils.toInteger(nId);
        if(Objects.isNull(nIdInteger)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid National Id Format!"));
        }

        return ResponseEntity.ok(proceduresRepository.getTicketAccountCustomers(nIdInteger));

    }

    @Transactional
    @PostMapping("/highest-voucher-value")
    public ResponseEntity<?> getHighestVoucherValue(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        return ResponseEntity.ok(proceduresRepository.getAccountHighestVoucher(mobileNum));
    }


    @Transactional
    @PostMapping("/remaining-plan-amount")
    public ResponseEntity<?> getRemainingPlanAmount(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum") || !requestParams.containsKey("planName") )
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        String planName = requestParams.get("planName");

        return ResponseEntity.ok(functionsRepository.getRemainingPlanAmount(mobileNum, planName));

    }

    @Transactional
    @PostMapping("/extra-plan-amount")
    public ResponseEntity<?> getExtraPlanAmount(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum") || !requestParams.containsKey("planName") )
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        String planName = requestParams.get("planName");

        return ResponseEntity.ok(functionsRepository.getExtraPlanAmount(mobileNum, planName));

    }

    @Transactional
    @PostMapping("/top-successful-payments")
    public ResponseEntity<?> getTopSuccessfulPayments(@RequestBody Map<String, String> requestParams)
    {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum"))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");

        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }

        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }


        return ResponseEntity.ok(proceduresRepository.topSuccessfulPayments(mobileNum));

    }



}


/* //TODO: 
- crosscheck mobile num with nId 



*/