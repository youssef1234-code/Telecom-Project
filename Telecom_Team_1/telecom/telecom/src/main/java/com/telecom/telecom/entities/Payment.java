package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Payment", schema = "dbo")
public class Payment {
    @Id
    @Column(name = "paymentID", nullable = false)
    private Integer id;

    @Column(name = "amount", precision = 10, scale = 1)
    private BigDecimal amount;

    @Column(name = "date_of_payment")
    private LocalDate dateOfPayment;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "status", length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mobileNo")
    private CustomerAccount mobileNo;

}