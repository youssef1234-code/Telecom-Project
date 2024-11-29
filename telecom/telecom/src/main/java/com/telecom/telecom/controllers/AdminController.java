package com.telecom.telecom.controllers;

import com.telecom.telecom.repositories.ProceduresRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    ProceduresRepository procedureRepository;

    @Transactional
    @GetMapping("/customer-accounts")
    public ResponseEntity<?> getCustomerAccounts() {
        return ResponseEntity.ok(procedureRepository.getAllAccountsPlans());
    }


}
