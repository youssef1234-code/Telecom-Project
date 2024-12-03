package com.telecom.telecom.controllers;


import com.telecom.telecom.repositories.FunctionsRepository;
import com.telecom.telecom.repositories.ProceduresRepository;
import com.telecom.telecom.repositories.ViewsRepository;
import com.telecom.telecom.utils.HelperUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
public class AdminComponent2Controller {
    @Autowired
    FunctionsRepository functionsRepository;

    @Autowired
    ProceduresRepository proceduresRepository;

    @Autowired
    ViewsRepository viewsRepository;

    @Transactional
    @GetMapping("/wallet-details")
    public ResponseEntity<?> getWalletDetails() {
        return ResponseEntity.ok(viewsRepository.getCustomerWallet());
    }

    @Transactional
    @GetMapping("/e-shops")
    public ResponseEntity<?> getEShops() {
        return ResponseEntity.ok(viewsRepository.getE_shopVouchers());
    }

    @Transactional
    @GetMapping("/payments")
    public ResponseEntity<?> getPayments() {
        return ResponseEntity.ok(viewsRepository.getAccountPayments());
    }

    @Transactional
    @GetMapping("/cashback-transactions")
    public ResponseEntity<?> getCashbackTransaction() {
        return ResponseEntity.ok(viewsRepository.getNumOfCashback());
    }

    @Transactional
    @PostMapping("/accepted-transactions")
    public ResponseEntity<?> acceptTransaction(@RequestBody Map<String, String> reqParam) {
        if(reqParam.isEmpty() || !reqParam.containsKey("mobileNum")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }
        String mobileNum = reqParam.get("mobileNum");
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

    @Transactional
    @PostMapping("/wallet-cashback")
    public ResponseEntity<?> walletCashback(@RequestBody Map<String, String> reqParam) {
        if(reqParam.isEmpty() || !reqParam.containsKey("walletID")|| !reqParam.containsKey("planID")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }
        String walletIDS = reqParam.get("walletID");
        String planIDS = reqParam.get("planID");

        if(Strings.isBlank(walletIDS)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Wallet ID cannot be empty!"));
        }

        if(Strings.isBlank(planIDS)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Plan ID cannot be empty!"));
        }

        Integer walletID = HelperUtils.toInteger(walletIDS);
        if(Objects.isNull(walletID)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid wallet ID format!"));
        }

        Integer planID = HelperUtils.toInteger(planIDS);
        if(Objects.isNull(planID)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid plan ID format!"));
        }

        return ResponseEntity.ok(functionsRepository.getWalletCashbackAmount(walletID, planID));

    }

    @Transactional
    @PostMapping("/average-transactions")
    public ResponseEntity<?> averageTransactions(@RequestBody Map<String, String> reqParam) {
        if(reqParam.isEmpty() || !reqParam.containsKey("walletID") || !reqParam.containsKey("startDate") || !reqParam.containsKey("endDate")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }

        String walletIDS = reqParam.get("walletID");
        String startDate = reqParam.get("startDate");
        String endDate = reqParam.get("endDate");

        if(Strings.isBlank(walletIDS)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Wallet ID cannot be empty!"));
        }

        if(Strings.isBlank(startDate)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Start date cannot be empty!"));
        }

        if(Strings.isBlank(endDate)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "End date cannot be empty!"));
        }

        Integer walletID = HelperUtils.toInteger(walletIDS);
        if(Objects.isNull(walletID)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid wallet ID format!"));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate formattedStartDate = HelperUtils.toDateFormat(startDate, formatter);
        if(Objects.isNull(formattedStartDate)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid Date format!"));
        }

        LocalDate formattedEndDate = HelperUtils.toDateFormat(endDate, formatter);
        if(Objects.isNull(formattedEndDate)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid Date format!"));
        }

        return ResponseEntity.ok(functionsRepository.getWalletTransferAmount(walletID, formattedStartDate, formattedEndDate));
    }

    @Transactional
    @PostMapping("/wallet-linking")
    public ResponseEntity<?> walletLinking(@RequestBody Map<String, String> reqParam) {
        if(reqParam.isEmpty() || !reqParam.containsKey("mobileNum")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }
        String mobileNum = reqParam.get("mobileNum");
        if(Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }
        if(mobileNum.length()!=11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        return ResponseEntity.ok(Map.of("isLinked",functionsRepository.getMobileNo(mobileNum)));
    }

    @Transactional
    @PostMapping("/update-points")
    public ResponseEntity<?> updatePoints(@RequestBody Map<String, String> reqParam) {
        if (reqParam.isEmpty() || !reqParam.containsKey("mobileNum")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Make sure to enter the required info!"));
        }
        String mobileNum = reqParam.get("mobileNum");
        if (Strings.isBlank(mobileNum)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number cannot be empty!"));
        }
        if (mobileNum.length() != 11) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Mobile Number must be 11 characters long!"));
        }

        try{
            proceduresRepository.totalPointsAccount(mobileNum);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false));
        }
    }


}
