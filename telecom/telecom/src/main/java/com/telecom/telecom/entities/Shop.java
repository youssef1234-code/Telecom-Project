package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Shop", schema = "dbo")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopID", nullable = false)
    private Integer id;  // The primary key in Shop entity

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "category", length = 50)
    private String category;

    @OneToOne(mappedBy = "shop")  // Correct 'mappedBy' to reference 'shop' in EShop
    private EShop eShop;

    @OneToOne(mappedBy = "shop")  // Correct 'mappedBy' to reference 'shop' in PhysicalShop
    private PhysicalShop physicalShop;

    @OneToMany(mappedBy = "shopID")
    private Set<Voucher> vouchers = new LinkedHashSet<>();
}
