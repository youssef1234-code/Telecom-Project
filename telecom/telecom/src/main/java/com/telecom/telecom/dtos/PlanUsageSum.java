package com.telecom.telecom.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlanUsageSum implements Serializable {
    private static final long serialVersionUID = 1L;

    private int planId;
    private int totalData;
    private int totalMins;
    private int totalSMS;
}
