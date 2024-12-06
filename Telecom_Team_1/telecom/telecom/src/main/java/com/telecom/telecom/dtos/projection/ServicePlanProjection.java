package com.telecom.telecom.dtos.projection;

public interface ServicePlanProjection {

    public Integer getPlanID();
    public String getName();
    public Integer getPrice();
    public Integer getSmsOffered();
    public Integer getMinutesOffered();
    public Integer getDataOffered();
    public String getDescription();

}
