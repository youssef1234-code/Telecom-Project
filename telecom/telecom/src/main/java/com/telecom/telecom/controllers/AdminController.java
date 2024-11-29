package com.telecom.telecom.controllers;

import com.telecom.telecom.repositories.FunctionsRepository;
import com.telecom.telecom.repositories.ProceduresRepository;
import com.telecom.telecom.repositories.ViewsRepository;
import com.telecom.telecom.utils.HelperUtils;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    ProceduresRepository procedureRepository;

    @Autowired
    ViewsRepository viewsRepository;

    @Autowired
    FunctionsRepository functionsRepository;

    @Transactional
    @GetMapping("/customer-accounts")
    public ResponseEntity<?> getCustomerAccounts() {
        return ResponseEntity.ok(procedureRepository.getAllAccountsPlans());
    }

    @Transactional
    @GetMapping("/customer-profiles")
    public ResponseEntity<?> getProfiles() {
        return ResponseEntity.ok(viewsRepository.getAllProfileAccounts());
    }

    @Transactional
    @GetMapping("/store-vouchers")
    public ResponseEntity<?> getStoreVouchers() {
        return ResponseEntity.ok(viewsRepository.getStoreVouchers());
    }

    @Transactional
    @GetMapping("/resolved-tickets")
    public ResponseEntity<?> getResolvedTickets() {
        return ResponseEntity.ok(viewsRepository.getResolvedTickets());
    }

    @Transactional
    @PostMapping("/subscribed-plan")
    public ResponseEntity<?> getSubiscribedPlanFromDate(@RequestBody Map<String, String> requestParams) {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum") || !requestParams.containsKey("planId")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String planId = requestParams.get("planId");
        String startDate = requestParams.get("startDate");

        if(Strings.isBlank(planId) || Strings.isBlank(startDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number and Start Date cannot be empty!"));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        LocalDate formattedstartDate = HelperUtils.toDateFormat(startDate, formatter);
        if(Objects.isNull(formattedstartDate)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid Date Format!"));
        }
        Integer planIdInt = HelperUtils.toInteger(planId);
        if(Objects.isNull(planIdInt)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid Plan Id Format!"));
        }
        return ResponseEntity.ok(functionsRepository.getAccountPlanDate(formattedstartDate,planIdInt));
    }


}
