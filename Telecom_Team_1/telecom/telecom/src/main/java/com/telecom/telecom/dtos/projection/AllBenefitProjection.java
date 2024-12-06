package com.telecom.telecom.dtos.projection;

import java.time.LocalDate;

public interface AllBenefitProjection {
    Integer getBenefitID();
    String getDescription();
    LocalDate getValidityDate();
    String getStatus();
    String getMobileNo();
}
