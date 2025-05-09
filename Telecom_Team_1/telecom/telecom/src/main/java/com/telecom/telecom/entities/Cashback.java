package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Cashback {
    @EmbeddedId
    private CashbackId id;

    @MapsId("benefitID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "benefitID", nullable = false)
    private Benefit benefitID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walletID")
    private Wallet walletID;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "credit_date")
    private LocalDate creditDate;

}