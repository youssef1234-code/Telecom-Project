package com.telecom.telecom.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPoints implements Serializable {
    private static final long serialVersionUID = 1L;

    private int paymentCount;
    private BigDecimal totalPoints;
}
