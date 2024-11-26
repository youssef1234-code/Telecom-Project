package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "Process_Payment")
public class ProcessPayment {
    @Id
    @Column(name = "paymentID", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paymentID", nullable = false)
    private com.telecom.telecom.entities.Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planID")
    private com.telecom.telecom.entities.ServicePlan planID;

    @Column(name = "remaining_balance", precision = 10, scale = 1)
    private BigDecimal remainingBalance;

    @Column(name = "extra_amount", precision = 10, scale = 1)
    private BigDecimal extraAmount;

}