package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "Process_Payment", schema = "dbo")
public class ProcessPayment {
    @Id
    @Column(name = "paymentID", nullable = false)
    private Integer paymentID;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paymentID", nullable = false)
    private Payment payment;  // Ensure 'paymentID' matches the 'id' in Payment entity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planID")
    private ServicePlan planID;  // ServicePlan type should be referenced directly (no need for fully qualified name)

    @Column(name = "remaining_balance", precision = 10, scale = 1)
    private BigDecimal remainingBalance;

    @Column(name = "extra_amount", precision = 10, scale = 1)
    private BigDecimal extraAmount;
}
