package com.telecom.telecom.dtos.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CustomerWalletProjection {
    Integer getWalletID();
    BigDecimal getCurrentBalance();
    String getCurrency();
    LocalDate getLastModifiedDate();
    Integer getNationalID();
    String getMobileNo();
    String getFirstName();
    String getLastName();
}
