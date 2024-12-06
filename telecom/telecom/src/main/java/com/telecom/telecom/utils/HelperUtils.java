package com.telecom.telecom.utils;

import com.telecom.telecom.dtos.projection.MonthUsageProjection;
import com.telecom.telecom.repositories.FunctionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class HelperUtils {
    @Autowired
    FunctionsRepository functionsRepository;

    public static List<Map<String, Object>> flattenUsageProjections(List<MonthUsageProjection> usages) {
        AtomicInteger sequentialId = new AtomicInteger(1); // For sequential Id

        return usages.stream()
                .map(usage -> {
                    Map<String, Object> flatMap = new HashMap<>();
                    flatMap.put("SequentialId", sequentialId.getAndIncrement()); // Adding sequential Id
                    flatMap.put("totalData", usage.getDataConsumption()); // Flattening fields
                    flatMap.put("totalMins", usage.getMinutesUsed());
                    flatMap.put("totalSMS", usage.getSmsSent());
                    return flatMap;
                })
                .collect(Collectors.toList());
    }

    // Helper method to validate date format
    public static LocalDate toDateFormat(String date, DateTimeFormatter formatter) {
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    public static Integer toInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static BigDecimal toBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static ResponseEntity<?> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", message));
    }

    public static ResponseEntity<?> success(String message) {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", message));
    }


    @Transactional
    public Boolean validateMobileNumberAndNId(String mobileNumber, Integer nId) {
        Integer valid = functionsRepository.checkMobileAgainstNId(mobileNumber, nId);
        return !valid.equals(0);
    }
}
