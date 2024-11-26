package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "shop", schema = "dbo")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopID", nullable = false)
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "Category", length = 50)
    private String category;

    @OneToOne(mappedBy = "shopID")
    private EShop eShop;

    @OneToOne(mappedBy = "shopID")
    private PhysicalShop physicalShop;

    @OneToMany(mappedBy = "shopid")
    private Set<Voucher> vouchers = new LinkedHashSet<>();

}