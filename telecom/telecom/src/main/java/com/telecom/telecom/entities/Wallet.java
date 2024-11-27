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
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "walletID", nullable = false)
    private Integer id;

    @ColumnDefault("0")
    @Column(name = "current_balance", precision = 10, scale = 2)
    private BigDecimal currentBalance;

    @ColumnDefault("'egp'")
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
    private Set<TransferMoney> transferFrom = new LinkedHashSet<>();

    @OneToMany(mappedBy = "walletID2")
    private Set<TransferMoney> tranferTo = new LinkedHashSet<>();

}