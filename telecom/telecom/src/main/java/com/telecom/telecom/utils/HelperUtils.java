package com.telecom.telecom.utils;

import com.telecom.telecom.repositories.FunctionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class HelperUtils {
    @Autowired
    FunctionsRepository functionsRepository;

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

    @Transactional
    public Boolean validateMobileNumberAndNId(String mobileNumber, Integer nId) {
        Integer valid = functionsRepository.checkMobileAgainstNId(mobileNumber, nId);
        return !valid.equals(0);
    }
}
