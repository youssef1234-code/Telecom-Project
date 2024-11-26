package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "E_shop")
public class EShop {
    @Id
    @Column(name = "shopID", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shopID", nullable = false)
    private com.telecom.telecom.entities.Shop shop;

    @Column(name = "URL", length = 50)
    private String url;

    @Column(name = "rating")
    private Integer rating;

}