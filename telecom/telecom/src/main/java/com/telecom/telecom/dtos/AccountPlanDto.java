package com.telecom.telecom.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountPlanDto {
    private String mobileNo;
    private Integer planId;
    //name here is plan name NOT ACCOUNT NAME!
    private String name;
}