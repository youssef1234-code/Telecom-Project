package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Customer_Account", schema = "dbo")
public class CustomerAccount {
    @Id
    @Column(name = "mobileNo", nullable = false, length = 11)
    private String mobileNo;

    @Column(name = "pass", length = 50)
    private String pass;

    @Column(name = "balance", precision = 10, scale = 1)
    private BigDecimal balance;

    @Column(name = "account_type", length = 50)
    private String accountType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "status", length = 50)
    private String status;

    @ColumnDefault("0")
    @Column(name = "point")
    private Integer point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nationalID")
    private CustomerProfile nationalID;

    @OneToMany(mappedBy = "mobileNo")
    private Set<Benefit> benefits = new LinkedHashSet<>();

    @OneToMany(mappedBy = "mobileNo")
    private Set<com.telecom.telecom.entities.Payment> payments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "mobileNo")
    private Set<com.telecom.telecom.entities.PlanUsage> planUsages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "mobileNo")
    private Set<com.telecom.telecom.entities.Subscription> subscriptions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "mobileNo")
    private Set<com.telecom.telecom.entities.TechnicalSupportTicket> technicalSupportTickets = new LinkedHashSet<>();

    @OneToMany(mappedBy = "mobileNo")
    private Set<com.telecom.telecom.entities.Voucher> vouchers = new LinkedHashSet<>();

}