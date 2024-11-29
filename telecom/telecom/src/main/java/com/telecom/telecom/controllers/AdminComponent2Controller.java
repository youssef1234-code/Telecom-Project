package com.telecom.telecom.controllers;


import com.telecom.telecom.repositories.FunctionsRepository;
import com.telecom.telecom.repositories.ProceduresRepository;
import com.telecom.telecom.repositories.ViewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
