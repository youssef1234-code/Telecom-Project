package com.telecom.telecom.dtos.projection;

public interface MonthUsageProjection {
    Integer getDataConsumption();
    Integer getMinutesUsed();
    Integer getSmsSent();

}
