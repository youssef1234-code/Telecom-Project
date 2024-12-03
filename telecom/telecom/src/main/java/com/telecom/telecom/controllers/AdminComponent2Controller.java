package com.telecom.telecom.controllers;


import com.telecom.telecom.repositories.FunctionsRepository;
import com.telecom.telecom.repositories.ProceduresRepository;
import com.telecom.telecom.repositories.ViewsRepository;
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

import java.util.Map;

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
        String walletID = reqParam.get("walletID");
        String planID = reqParam.get("planID");

        if(Strings.isBlank(walletID)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Wallet ID cannot be empty!"));
        }

        if(Strings.isBlank(planID)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Plan ID cannot be empty!"));
        }

        return ResponseEntity.ok(functionsRepository.getWalletCashbackAmount(Integer.parseInt(walletID), Integer.parseInt(planID)));

    }


}
