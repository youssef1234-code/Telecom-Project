package com.telecom.telecom.dtos.projection;

import java.time.LocalDate;

public interface BenefitProjection {
    Integer getBenefitID();
    String getDescription();
    LocalDate getValidityDate();
    String getStatus();
}