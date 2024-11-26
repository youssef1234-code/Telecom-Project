package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucherID", nullable = false)
    private Integer id;

    @Column(name = "\"value\"")
    private Integer value;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "points")
    private Integer points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mobileNo")
    private com.telecom.telecom.entities.CustomerAccount mobileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopID")
    private com.telecom.telecom.entities.Shop shopID;

    @Column(name = "redeem_date")
    private LocalDate redeemDate;

}