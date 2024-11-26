package com.telecom.telecom.dtos;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerAccountDto implements Serializable {
    public static final long serialVersionUID = 1L;

    private String mobileNo;
    private String password;
    private BigDecimal balance;

}
