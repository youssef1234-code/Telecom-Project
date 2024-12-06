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
        if(requestParams.isEmpty() || !requestParams.containsKey("startDate") || !requestParams.containsKey("planId")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String planId = requestParams.get("planId");
        String startDate = requestParams.get("startDate");

        if(Strings.isBlank(planId) || Strings.isBlank(startDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Plan Id and Start Date cannot be empty!"));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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

    @Transactional
    @PostMapping("/planUsage-sum")
    public ResponseEntity<?> getPlanSum(@RequestBody Map<String, String> requestParams) {
        if(requestParams.isEmpty() || !requestParams.containsKey("startDate") || !requestParams.containsKey("mobileNum")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");
        String startDate = requestParams.get("startDate");

        if(Strings.isBlank(mobileNum) || Strings.isBlank(startDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number and Start Date cannot be empty!"));
        }
        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formattedstartDate = HelperUtils.toDateFormat(startDate, formatter);
        if(Objects.isNull(formattedstartDate)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid Date Format!"));
        }
        return ResponseEntity.ok(functionsRepository.getPlanConsumptionsFromDate(mobileNum,formattedstartDate));
    }

    @Transactional
    @PostMapping("/sms-offers")
    public ResponseEntity<?> getSMSOffers(@RequestBody Map<String, String> requestParams) {
        if(requestParams.isEmpty() || !requestParams.containsKey("mobileNum")) {
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
        return ResponseEntity.ok(functionsRepository.getAccountOfferedSMS(mobileNum));
    }

    @Transactional
    @PostMapping("/delete-benefits")
    public ResponseEntity<?> deleteBenefitsForMobile(@RequestBody Map<String, String> requestParams) {
        if(requestParams.isEmpty() || !requestParams.containsKey("planId") || !requestParams.containsKey("mobileNum")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");
        String planIdS = requestParams.get("planId");

        if(Strings.isBlank(mobileNum) || Strings.isBlank(planIdS)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number and Plan ID cannot be empty!"));
        }
        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }
        Integer planId = HelperUtils.toInteger(planIdS);
        if(Objects.isNull(planId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid Plan Id Format!"));
        }
        try{
            procedureRepository.deletePlanBenefitsFromAccount(mobileNum,planId);
            return ResponseEntity.ok(Map.of("successMessage", "Deleted successfully!"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Deletion failed for Mobile Number: " + mobileNum + " And Plan ID: " + planId + "!"));
        }
    }

    @Transactional
    @PostMapping("/get-benefits")
    public ResponseEntity<?> getBenefitsForMobile(@RequestBody Map<String, String> requestParams) {
        if(requestParams.isEmpty() || !requestParams.containsKey("planId") || !requestParams.containsKey("mobileNum")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String mobileNum = requestParams.get("mobileNum");
        String planIdS = requestParams.get("planId");

        if(Strings.isBlank(mobileNum) || Strings.isBlank(planIdS)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number and Plan ID cannot be empty!"));
        }
        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }
        Integer planId = HelperUtils.toInteger(planIdS);
        if(Objects.isNull(planId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid Plan Id Format!"));
        }
        return ResponseEntity.ok(functionsRepository.getBenefitsByMobileAndPlanId(mobileNum,planId));
    }


}
