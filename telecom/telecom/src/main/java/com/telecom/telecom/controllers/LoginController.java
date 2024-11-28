package com.telecom.telecom.controllers;

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
    public ResponseEntity<Object> validateAdminLogin(@RequestBody AdminLoginRequest loginRequest) {
        // Validate input
        if (Strings.isBlank(loginRequest.adminId()) || Strings.isBlank(loginRequest.password())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
        }

        // Simulate admin login (enhance with DB query or external service if needed)
        if (loginRequest.adminId().equals("admin") && loginRequest.password().equals("admin")) {
            String token = "admin-token-123";
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", "admin");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid admin credentials");
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<Object> validateCustomerLogin(@RequestBody CustomerLoginRequest loginRequest) {
        // Validate input
        if (Strings.isBlank(loginRequest.mobileNo()) || Strings.isBlank(loginRequest.password())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");
        }

        Boolean allowed = functionsRepository.validateLogin(loginRequest.mobileNo(), loginRequest.password());
        if (Boolean.TRUE.equals(allowed)) {
            String token = "customer-token-"+loginRequest.mobileNo();
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", "customer");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid customer credentials");
        }
    }

    // Record for login requests -> records are objects with immutable parameters
    public record AdminLoginRequest(String adminId, String password) {}
    public record CustomerLoginRequest(String mobileNo, String password) {}

}
