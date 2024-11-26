package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Cashback {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benefitID")
    private com.telecom.telecom.entities.Benefit benefitID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walletID")
    private com.telecom.telecom.entities.Wallet walletID;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "credit_date")
    private LocalDate creditDate;

}