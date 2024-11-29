package com.telecom.telecom.dtos.projection;

import java.math.BigDecimal;

public interface PaymentPointsProjection {
    Integer getPaymentCount();
    BigDecimal getTotalPoints();
}
