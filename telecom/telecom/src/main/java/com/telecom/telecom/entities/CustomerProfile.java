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
@Table(name = "Customer_profile", schema = "dbo")
public class CustomerProfile {
    @Id
    @Column(name = "nationalID", nullable = false)
    private Integer id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "nationalID")
    private Set<CustomerAccount> customerAccounts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "nationalID")
    private Set<com.telecom.telecom.entities.Wallet> wallets = new LinkedHashSet<>();

}