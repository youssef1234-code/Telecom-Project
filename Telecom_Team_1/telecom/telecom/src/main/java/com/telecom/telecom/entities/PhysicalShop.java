package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Physical_shop")
public class PhysicalShop {
    @Id
    @Column(name = "shopID", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shopID", nullable = false)
    private Shop shop;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "working_hours", length = 50)
    private String workingHours;

}