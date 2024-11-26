package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter@Setter
@Table(name = "customer_account", schema = "dbo")
public class CustomerAccount {
    @Id
    private String mobileNo;

    private String pass;

    @Column(precision = 10, scale = 1)
    private BigDecimal balance;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;


    @Temporal(TemporalType.DATE)
    private Date start_date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private int point = 0;

    @ManyToOne
    @JoinColumn(name = "nationalID")
    private CustomerProfile customerProfile;


    public enum AccountType {
        POSTPAID, PREPAID, PAY_AS_YOU_GO
    }

    public enum AccountStatus {
        ACTIVE, ONHOLD
    }
}
