package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customer_profile", schema = "dbo")
public class CustomerProfile {
    @Id
    @Column(name = "nationalID", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "address", nullable = false, length = 70)
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "nationalID")
    private Set<Wallet> wallets = new LinkedHashSet<>();

    @OneToMany(mappedBy = "nationalID")
    private Set<CustomerAccount> customerAccounts = new LinkedHashSet<>();

}