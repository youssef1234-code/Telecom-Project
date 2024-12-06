package com.telecom.telecom.controllers;

import com.telecom.telecom.dtos.requests.AdminLoginRequest;
import com.telecom.telecom.dtos.requests.CustomerLoginRequest;
import com.telecom.telecom.repositories.FunctionsRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    FunctionsRepository functionsRepository;

    @PostMapping("/admin")
    public ResponseEntity<?> validateAdminLogin(@RequestBody AdminLoginRequest loginRequest) {
        // Validate input
        if (Strings.isBlank(loginRequest.getAdminId()) || Strings.isBlank(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Invalid  credentials"));
        }

        // Simulate admin login (enhance with DB query or external service if needed)
        if (loginRequest.getAdminId().equals("admin") && loginRequest.getPassword().equals("admin")) {
            String token = "admin-token-123";
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", "admin");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Invalid admin credentials"));

        }
    }

    @PostMapping("/customer")
    public ResponseEntity<?> validateCustomerLogin(@RequestBody CustomerLoginRequest loginRequest) {
        // Validate input
        if (Strings.isBlank(loginRequest.getMobileNo()) || Strings.isBlank(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message","Invalid credentials"));
        }

        Integer allowed = functionsRepository.validateLogin(loginRequest.getMobileNo(), loginRequest.getPassword());
        if (allowed!=null && allowed.equals(1)) {
            String token = "customer-token-"+loginRequest.getMobileNo();
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", "customer");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Invalid Customer credentials"));

        }
    }

}
