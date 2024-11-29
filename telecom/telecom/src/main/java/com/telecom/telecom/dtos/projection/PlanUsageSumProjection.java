package com.telecom.telecom.dtos.projection;

public interface PlanUsageSumProjection {

    Integer getPlanId();
    Integer getTotalData();
    Integer getTotalMins();
    Integer getTotalSMS();
}
