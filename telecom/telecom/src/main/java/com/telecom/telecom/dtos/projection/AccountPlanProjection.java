package com.telecom.telecom.dtos.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AccountPlanProjection {
    String getMobileNo();
    String getPass();
    BigDecimal getBalance();
    String getAccountType();
    LocalDate getStartDate();
    String getStatus();
    Integer getPoints();
    Integer getNationalId();
    Integer getPlanId();
    String getPlanName();
    Integer getPrice();
    Integer getSMSOffered();
    Integer getMinutesOffered();
    Integer getDataOffered();
    String getDescription();
}
