package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopID", nullable = false)
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "category", length = 50)
    private String category;

    @OneToOne(mappedBy = "shopID")
    private com.telecom.telecom.entities.EShop eShop;

    @OneToOne(mappedBy = "shopID")
    private com.telecom.telecom.entities.PhysicalShop physicalShop;

    @OneToMany(mappedBy = "shopID")
    private Set<com.telecom.telecom.entities.Voucher> vouchers = new LinkedHashSet<>();

}