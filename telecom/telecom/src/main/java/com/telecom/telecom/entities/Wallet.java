package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Wallet", schema = "dbo")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "walletID", nullable = false)
    private Integer id;

    @Column(name = "current_balance", precision = 10, scale = 2)
    private BigDecimal currentBalance;

    @Column(name = "currency", length = 50)
    private String currency;

    @Column(name = "last_modified_date")
    private LocalDate lastModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nationalID")
    private CustomerProfile nationalID;

    @Column(name = "mobileNo", length = 11)
    private String mobileNo;

    @OneToMany(mappedBy = "walletID")
    private Set<Cashback> cashbacks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "walletID1")
    private Set<TransferMoney> transferedFrom = new LinkedHashSet<>();

    @OneToMany(mappedBy = "walletID2")
    private Set<TransferMoney> transferedTo = new LinkedHashSet<>();

}