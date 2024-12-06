package com.telecom.telecom.dtos.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ProfileAccountProjection {
    // Customer Profile Fields
    String getNationalID();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getAddress();
    LocalDate getDateOfBirth();

    // Customer Account Fields
    String getMobileNo();
    String getAccountType();
    String getStatus();
    LocalDate getStartDate();
    BigDecimal getBalance();
    Integer getPoints();
}

