package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "process_payment")
public class ProcessPayment {
    @Id
    @Column(name = "paymentID", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paymentID", nullable = false)
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planID")
    private ServicePlan planID;

    @ColumnDefault("[dbo].[function_remaining_amount]([paymentID],[planID])")
    @Column(name = "remaining_amount")
    private Integer remainingAmount;

    @ColumnDefault("[dbo].[function_extra_amount]([paymentID],[planID])")
    @Column(name = "extra_amount")
    private Integer extraAmount;

}