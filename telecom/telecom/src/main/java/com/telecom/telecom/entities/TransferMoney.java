package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Transfer_money", schema = "dbo")
public class TransferMoney {
    @EmbeddedId
    private TransferMoneyId id;

    @MapsId("walletID1")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "walletID1", nullable = false)
    private com.telecom.telecom.entities.Wallet walletID1;

    @MapsId("walletID2")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "walletID2", nullable = false)
    private com.telecom.telecom.entities.Wallet walletID2;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "transfer_date")
    private LocalDate transferDate;

}