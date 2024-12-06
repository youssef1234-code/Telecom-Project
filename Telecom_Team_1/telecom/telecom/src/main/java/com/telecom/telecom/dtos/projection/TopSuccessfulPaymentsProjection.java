package com.telecom.telecom.dtos.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TopSuccessfulPaymentsProjection {
    Integer getPaymentID();
    BigDecimal getAmount();
    LocalDate getDateOfPayment();
    String getPaymentMethod();
    String getStatus();
    String getMobileNum();

}
