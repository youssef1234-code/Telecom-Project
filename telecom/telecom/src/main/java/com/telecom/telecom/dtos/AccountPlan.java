package com.telecom.telecom.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountPlan implements Serializable {
    private static final long serialVersionUID = 1L;

    // Fields from customer_account
    private String mobileNo;
    private String password;
    private BigDecimal balance;
    private String accountType;
    private LocalDate startDate;
    private String status;
    private Integer points;
    private Integer nationalId;

    // Fields from Service_plan
    private Integer planId;
    private String planName;
    private Integer price;
    private Integer smsOffered;
    private Integer minutesOffered;
    private Integer dataOffered;
    private String planDescription;

}
