package com.telecom.telecom.dtos.projection;

public interface PhysicalStoreVoucherProjection {
    Integer getShopId();
    String getAddress();
    String getWorkingHours();
    Integer getVoucherId();
    Integer getValue();
}