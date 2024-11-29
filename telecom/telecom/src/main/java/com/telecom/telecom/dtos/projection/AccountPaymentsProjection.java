package com.telecom.telecom.dtos.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AccountPaymentsProjection {
    Integer getPaymentID();
    BigDecimal getAmount();
    LocalDate getDateOfPayment();
    String getPaymentMethod();
    String getStatus();
    String getMobileNo();
}
