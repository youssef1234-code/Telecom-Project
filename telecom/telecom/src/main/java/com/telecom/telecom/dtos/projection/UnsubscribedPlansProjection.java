package com.telecom.telecom.dtos.projection;

public interface UnsubscribedPlansProjection {
    Integer getPlanID();
    String getPlanName();
    Integer getPrice();
    Integer getSMSOffered();
    Integer getMinutesOffered();
    Integer getDataOffered();
    String getDescription();
}
