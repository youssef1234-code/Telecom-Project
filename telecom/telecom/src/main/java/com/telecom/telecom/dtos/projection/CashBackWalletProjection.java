package com.telecom.telecom.dtos.projection;

import java.time.LocalDate;

public interface CashBackWalletProjection {
    Integer getCashbackID();
    Integer getBenefitID();
    Integer getWalletID();
    Integer getAmount();
    LocalDate getCreditDate();
}
